package com.tdpc.toko99.app

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
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
import com.tdpc.toko99.R
import com.tdpc.toko99.module.PiutangScreen
import com.tdpc.toko99.module.home.HomeScreen
import com.tdpc.toko99.module.master.MasterBarangScreen
import com.tdpc.toko99.module.store.StoreScreen
import com.tdpc.toko99.ui.common.remeberMyNavDrawerState
import com.tdpc.toko99.ui.navigation.NavigationItem
import com.tdpc.toko99.ui.navigation.Screen
import com.tdpc.toko99.ui.theme.Toko99Theme

@Composable
fun Toko99App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val appState = remeberMyNavDrawerState()
    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    when (currentRoute) {
//                        Screen.Home.route -> {
//                            navController.navigate("store"){
//                                popUpTo("home")
//                            }
//                        }
//                        Screen.Piutang.route ->{
//                            Toast.makeText(context,"this is piutang",Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                },
//                shape = RoundedCornerShape(16.dp),
//                backgroundColor = MaterialTheme.colors.primary
//            ) {
//                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add FAB")
//            }
//        },
        scaffoldState = appState.scaffoldState,
        topBar = {
            MyTopBar(
                onMenuClick = { appState.onMenuClick() }
            )
        },
        drawerContent = {
            MyDrawerContent(
                onBackPress = appState::onBackPress,
                navHostController = navController,
                onItemSelected = appState::onItemSelected,
            )
        },
        drawerGesturesEnabled = appState.scaffoldState.drawerState.isOpen
    )
    { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
//            composable(Screen.Home.route) {
//                HomeScreen(
//                    navigateToDetail = { megamanId ->
//                        navController.navigate(Screen.DetailMegaman.createRoute(megamanId))
//                    })
//            }
            composable(Screen.Piutang.route) {
                PiutangScreen()
            }
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Store.route) {
                StoreScreen()
            }
            composable(Screen.MasterBarang.route) {
                MasterBarangScreen()
            }
//            composable(
//                route = Screen.DetailMegaman.route,
//                arguments = listOf(navArgument("megamanId") { type = NavType.LongType })
//            ) {
//                val id = it.arguments?.getLong("megamanId") ?: -1L
//                DetailScreen(
//                    megamanId = id,
//                    navigateBack = {
//                        navController.navigateUp()
//                    },
//                    navigateToFavorite = {
//                        navController.popBackStack()
//                        navController.navigate(Screen.Favorite.route) {
//                            popUpTo(navController.graph.findStartDestination().id) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                )
//            }
        }
    }
}

@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onMenuClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
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
                Text(text = item.title, style = MaterialTheme.typography.subtitle2)
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
    Toko99Theme {
        Toko99App()
    }
}