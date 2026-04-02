package com.jczy.cyclone.club.flow_eventbus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventBus {
    private val _events = MutableSharedFlow<Any>()
    val events = _events.asSharedFlow()
    
    suspend fun post(event: Any) {
        _events.emit(event)
    }
}