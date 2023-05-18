package com.tdpc.toko99.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Piutang : Screen("piutang")
    object Store : Screen("store")
    object MasterBarang : Screen("master_barang")
    object AddDetailBarang : Screen("add_detail_barang")

//  object Profile : Screen("profile")
    object DetailBarang : Screen("home/{barangId}")
}