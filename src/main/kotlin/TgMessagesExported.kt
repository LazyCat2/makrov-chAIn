package io.github.lazycat2.makrovchain

import kotlinx.serialization.Serializable

@Serializable
data class TgMessagesExported(
    val messages: List<TgMessage>
)