package io.github.lazycat2.makrovchain

import kotlinx.serialization.json.encodeToJsonElement
import java.io.File

fun String.normalize() = this.filter { "".contains(it) }

class Ai(
    var dataset: List<ChainValue> = listOf(),
    var datasetPath: String? = null
) {
    fun gen(maxWords: Int): String {
        var currentWord: ChainValue? = dataset.random()
        var output = currentWord!!.word + " "

        (1..maxWords).forEach { _ ->
            if (currentWord == null) return@forEach

            val nextWord = mutableListOf<ChainNextWord>()
            currentWord!!.nextWord.forEach { (1..it.chance).forEach { _-> nextWord.add(it) } }

            val word = nextWord.random()

            output += word.word + " "
            currentWord = dataset.find { it.word == word.word }
        }

        return output
    }

    fun createDataset(sentences: List<String>) {
        val handled = mutableListOf<ChainValue>()
        sentences.forEach { datasetRow ->
            datasetRow.replace("\n", " ").split(" ").filterNot { it.isEmpty() || it.isBlank() }.apply {
                forEachIndexed { index, word ->
                    if (index == this.size - 1) return@forEachIndexed

                    val i = handled.indexOfFirst { it.word == word }
                    if (i != -1) {
                        val wordIndex = handled[i].nextWord.indexOfFirst { it.word == this[index + 1] }

                        if (wordIndex != -1)
                            handled[i].nextWord[wordIndex].chance++
                        else
                            handled[i].nextWord.add(ChainNextWord(this[index + 1], 1))
                    } else
                        handled.add(
                            ChainValue(
                                word, mutableListOf(ChainNextWord(this[index + 1], 1))
                            )
                        )
                }
            }
        }

        dataset = handled
    }

    fun loadDataset() {
        dataset = json.decodeFromString(File(datasetPath!!).readText())
    }

    fun saveDataset() = File(datasetPath!!).writeText(json.encodeToJsonElement(dataset).toString())
}