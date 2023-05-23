package com.tdpc.toko99.ui.common

import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class MainViewState(
    val isLoading: Boolean = false,
    val processSuccessEvent: StateEvent = consumed,
    val processSuccessEventWithTimestampEvent: StateEventWithContent<String> = consumed()
    )