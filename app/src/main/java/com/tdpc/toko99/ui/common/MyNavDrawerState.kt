package com.tdpc.toko99.ui.common

import android.app.Activity
import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MyNavDrawerState(
    val scaffoldState: ScaffoldState,
    private val scope: CoroutineScope,
    private val context: Context,
) {
    fun onMenuClick() {
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }

    fun onItemSelected() {
        scope.launch {
            scaffoldState.drawerState.close()
        }
    }

    fun onBackPress() {
        if (scaffoldState.drawerState.isOpen) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        } else if (scaffoldState.drawerState.isClosed) {
            scope.launch {
                val close = context as Activity
                close.finish()
            }
        }
    }
}

@Composable
fun remeberMyNavDrawerState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutinesScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,

): MyNavDrawerState = remember(scaffoldState, coroutinesScope, context) {
    MyNavDrawerState(scaffoldState, coroutinesScope, context)
}