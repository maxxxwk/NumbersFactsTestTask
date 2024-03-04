package com.maxxxwk.mornhousetesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.maxxxwk.mornhousetesttask.core.viewmodel.ViewModelFactory
import com.maxxxwk.mornhousetesttask.navigation.NavigationRoute
import com.maxxxwk.mornhousetesttask.screens.details.DetailsScreen
import com.maxxxwk.mornhousetesttask.screens.main.ui.MainScreen
import com.maxxxwk.mornhousetesttask.ui.theme.MornhouseTestTaskTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).component.inject(this)
        setContent {
            MornhouseTestTaskTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoute.MainScreen.route
                ) {
                    composable(
                        route = NavigationRoute.MainScreen.route
                    ) {
                        MainScreen(
                            viewModel = viewModel(factory = viewModelFactory),
                            openDetails = {
                                navController.navigate(
                                    NavigationRoute.DetailsScreen.passArguments(
                                        number = it.number,
                                        factText = it.factText
                                    )
                                )
                            }
                        )
                    }
                    composable(
                        route = NavigationRoute.DetailsScreen.route,
                        arguments = listOf(
                            navArgument(NavigationRoute.NUMBER_ARGUMENT_KEY) {
                                type = NavType.IntType
                            },
                            navArgument(NavigationRoute.FACT_ARGUMENT_KEY) {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        DetailsScreen(
                            number = checkNotNull(it.arguments?.getInt(NavigationRoute.NUMBER_ARGUMENT_KEY)),
                            factText = checkNotNull(it.arguments?.getString(NavigationRoute.FACT_ARGUMENT_KEY)),
                            onBack = {
                                navController.popBackStack(
                                    route = NavigationRoute.MainScreen.route,
                                    inclusive = false
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
