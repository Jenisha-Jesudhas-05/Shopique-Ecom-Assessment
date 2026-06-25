package com.example.shopique.navigation

sealed class Screen(val route: String) {

    object ProductList : Screen("product_list")

    object Login : Screen("login")

    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int): String {
            return "product_detail/$productId"
        }
    }

    object Cart : Screen("cart")

    object Wishlist : Screen("wishlist")

    object Checkout : Screen("checkout")

    object OrderSuccess : Screen("order_success")

    object StoreLocator : Screen("store_locator")

    object Profile : Screen("profile")
}


