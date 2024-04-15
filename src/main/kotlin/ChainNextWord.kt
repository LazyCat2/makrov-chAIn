package io.github.lazycat2.makrovchain

import kotlinx.serialization.Serializable

@Serializable
data class ChainNextWord(
    val word: String,
    var chance: Int
)