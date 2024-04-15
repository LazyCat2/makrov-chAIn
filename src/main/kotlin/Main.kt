package io.github.lazycat2.makrovchain

import kotlinx.serialization.json.*
import java.io.File

val json = Json { ignoreUnknownKeys = true }

fun main(a: Array<String>) {
    val args = (0..2).map { if (a.size-1 < it) "" else a[it] }

    when (args.first()) {
        "from-tg" ->
            Ai(datasetPath = args[2]).apply {
                val element = json.decodeFromString<JsonElement>(File(args[1]).readText())
                val messages = element.jsonObject["messages"]!!.jsonArray.map {

                    println(it)
                    val text = it.jsonObject["text"]!!

                    if (text is JsonPrimitive)
                        json.decodeFromString<String>(text.toString())
                    else {
                        var t = ""
                        text.jsonArray.forEach { elem ->
                            t += if (elem is JsonPrimitive)
                                json.decodeFromString<String>(text.toString())
                            else
                                json.decodeFromString<String>(text.jsonObject["text"].toString())
                        }
                        t
                    }
                }.filterNot { it.isBlank() || it.isEmpty() }

                createDataset(messages)
                saveDataset()
            }

        "from-txt" -> {
            Ai(datasetPath = args[2]).apply {
                createDataset(File(args[1]).readText().split("\n"))
                saveDataset()
            }
        }

        "generate" ->
            Ai(datasetPath = args[1]).apply {
                loadDataset()
                var maxWord = 25
                try {
                   maxWord = args[2].toInt()
                } catch (_:Throwable){}
                println(gen(maxWord))
            }

        else ->
            println("""
                from-tg [(required) path to telegram data] [(required) path to dataset]: Create dataset from exported telegram chat
                from-txt [(required) path to txt file] [(required) path to dataset]: Create dataset from plain text
                generate [(required) path to dataset] [(default = 25) max word amount]: Generate sentence from dataset
                """.trimIndent())
    }
}