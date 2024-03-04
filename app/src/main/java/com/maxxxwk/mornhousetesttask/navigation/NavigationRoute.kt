package com.maxxxwk.mornhousetesttask.navigation

sealed class NavigationRoute(val route: String) {
    data object MainScreen : NavigationRoute(route = "main")
    data object DetailsScreen : NavigationRoute(
        route = "details/{$NUMBER_ARGUMENT_KEY}/{$FACT_ARGUMENT_KEY}"
    ) {
        fun passArguments(number: Int, factText: String): String {
            return route.replace(
                "{$NUMBER_ARGUMENT_KEY}",
                number.toString()
            ).replace(
                "{$FACT_ARGUMENT_KEY}",
                factText
            )
        }
    }

    companion object {
        const val NUMBER_ARGUMENT_KEY = "number"
        const val FACT_ARGUMENT_KEY = "fact"
    }
}
