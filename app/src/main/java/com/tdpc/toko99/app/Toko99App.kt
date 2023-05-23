package com.tdpc.toko99.app

import android.Manifest
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.tdpc.toko99.R
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.module.PiutangScreen
import com.tdpc.toko99.module.detail.DetailScreen
import com.tdpc.toko99.module.detail.add.AddDetailScreen
import com.tdpc.toko99.module.home.HomeScreen
import com.tdpc.toko99.module.store.StoreScreen
import com.tdpc.toko99.ui.common.remeberMyNavDrawerState
import com.tdpc.toko99.ui.navigation.NavigationItem
import com.tdpc.toko99.ui.navigation.Screen
import com.tdpc.toko99.ui.theme.AppTheme
import com.tdpc.toko99.ui.theme.Shapes
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalPermissionsApi::class, ExperimentalCoilApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun Toko99App(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val appState = remeberMyNavDrawerState()
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    ModalNavigationDrawer(
        drawerState = appState.drawerState,
        scrimColor = MaterialTheme.colorScheme.scrim,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = Shapes.small
            ) {
                MyDrawerContent(
                    onBackPress = { appState.onBackPress() },
                    navHostController = navController,
                    onItemSelected = { appState.onItemSelected() },
                )
            }
        },
        gesturesEnabled = true,
        content = {
            Scaffold(
                floatingActionButton = {
                    if (currentRoute == Screen.Home.route) {
                        FloatingActionButton(
                            onClick = {
                                when (currentRoute) {
                                    Screen.Home.route -> {
                                        navController.navigate("store") {
                                            popUpTo("home")
                                        }
                                        cameraPermissionState.launchPermissionRequest()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add FAB")
                        }
                    }
                },
                topBar = {
                    MyTopBar(
                        onMenuClick = { appState.onMenuClick() }
                    )
                },
                contentColor = MaterialTheme.colorScheme.primary
            ) { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            navigateToDetail = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "barangModel",
                                    value = it
                                )
                                navController.navigate(Screen.DetailBarang.route)
                            }
                        )
                    }
                    composable(Screen.Piutang.route) {
                        PiutangScreen()
                    }
                    composable(Screen.Store.route) {
                        StoreScreen(
                            navigateToHome = {
                                navController.popBackStack()
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }

                            }
                        )
                    }
                    composable(route = Screen.AddDetailBarang.route) {
                        val barangModel =
                            navController.previousBackStackEntry?.savedStateHandle?.get<BarangModel>("barangModel")
                        if (barangModel != null) {
                            Log.i("barangModel", "$barangModel")
                            AddDetailScreen(
                                barangModel = barangModel,
                                navigateToHome = {
                                    navController.popBackStack()
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.AddDetailBarang.route) {
                                            inclusive = true
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                    composable(route = Screen.DetailBarang.route)
                    {
                        val barangModel =
                            navController.previousBackStackEntry?.savedStateHandle?.get<BarangModel>("barangModel")
                        if (barangModel != null) {
                            Log.i("barangModel", "$barangModel")
                            DetailScreen(
                                barangModel = barangModel,
                                navigateToAddDetail = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        key = "barangModel",
                                        value = it
                                    )
                                    navController.navigate(Screen.AddDetailBarang.route)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    TopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = {
                onMenuClick()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = stringResource(id = R.string.menu)
                )
            }
        },
        title = {
            Text(text = stringResource(id = R.string.app_name))
        }
    )
}

@Composable
fun MyDrawerContent(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit,
    navHostController: NavHostController,
    onItemSelected: () -> Unit,
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(
        NavigationItem(
            title = stringResource(id = R.string.home),
            icon = Icons.Default.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = stringResource(id = R.string.piutang),
            icon = Icons.Default.Person,
            screen = Screen.Piutang
        )
    )
    Column(
        modifier
            .fillMaxSize()
            .padding(top = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(126.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier
                    .matchParentSize(),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
            )
            Image(
                modifier = Modifier
                    .scale(1.4f),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider()
        for (item in items) {
            Row(
                modifier
                    .clickable {
                        navHostController.navigate(item.screen.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                        onItemSelected()
                    }
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(text = item.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.width(32.dp))
                if (currentRoute == item.screen.route) Icon(imageVector = Icons.Default.Check, contentDescription = "")
            }
        }
        Divider()
    }
    BackPressHandler {
        onBackPress()
    }
}

@Composable
fun BackPressHandler(enabled: Boolean = true, onBackPressed: () -> Unit) {
    val currentOnBackPressed by rememberUpdatedState(onBackPressed)
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = enabled
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme() {
        Toko99App()
    }
}