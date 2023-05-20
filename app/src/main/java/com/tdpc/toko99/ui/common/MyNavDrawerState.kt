package com.tdpc.toko99.ui.common

import android.app.Activity
import android.content.Context
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MyNavDrawerState(
    val drawerState: DrawerState,
    private val scope: CoroutineScope,
    private val context: Context,
) {
    fun onMenuClick() {
        scope.launch {
            drawerState.open()
        }
    }

    fun onItemSelected() {
        scope.launch {
            drawerState.close()
        }
    }

    fun onBackPress() {
        if (drawerState.isOpen) {
            scope.launch {
                drawerState.close()
            }
        } else if (drawerState.isClosed) {
            scope.launch {
                val close = context as Activity
                close.finish()
            }
        }
    }
}

@Composable
fun remeberMyNavDrawerState(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutinesScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,

): MyNavDrawerState = remember(drawerState, coroutinesScope, context) {
    MyNavDrawerState(drawerState, coroutinesScope, context)
}