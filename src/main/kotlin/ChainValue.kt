package io.github.lazycat2.makrovchain

import kotlinx.serialization.Serializable

@Serializable
data class ChainValue(
    val word: String,
    val nextWord: MutableList<ChainNextWord>
)