package com.tdpc.toko99.ui.navigation

import com.tdpc.toko99.R

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Piutang : Screen("piutang")
    object Store : Screen("store")
    object MasterBarang : Screen("master_barang")
//    object Profile : Screen("profile")
//    object DetailMegaman : Screen("home/{megamanId}") {
//        fun createRoute(megamanId: Long) = "home/$megamanId"
//    }
}