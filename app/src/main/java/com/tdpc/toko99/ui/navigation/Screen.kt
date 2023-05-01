package com.tdpc.toko99.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Piutang : Screen("piutang")
    object Store : Screen("store")
//    object Profile : Screen("profile")
//    object DetailMegaman : Screen("home/{megamanId}") {
//        fun createRoute(megamanId: Long) = "home/$megamanId"
//    }
}