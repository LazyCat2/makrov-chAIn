package io.github.lazycat2.makrovchain

import kotlinx.serialization.Serializable

@Serializable
data class TgMessage(
    val text: String
)