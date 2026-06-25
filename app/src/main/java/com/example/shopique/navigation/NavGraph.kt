package com.example.shopique.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shopique.presentation.auth.AuthViewModel
import com.example.shopique.presentation.auth.LoginScreen
import com.example.shopique.presentation.cart.CartScreen
import com.example.shopique.presentation.checkout.CheckoutScreen
import com.example.shopique.presentation.checkout.OrderSuccessScreen
import com.example.shopique.presentation.profile.ProfileScreen
import com.example.shopique.presentation.store.StoreLocatorScreen
import com.example.shopique.presentation.productdetail.ProductDetailScreen
import com.example.shopique.presentation.productlist.ProductListScreen
import com.example.shopique.presentation.wishlist.WishlistScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState by authViewModel.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = if (authState.isLoggedIn) Screen.ProductList.route else Screen.Login.route
    ) {
        // Login Screen
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.ProductList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Product List Screen
        composable(route = Screen.ProductList.route) {
            ProductListScreen(
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                },
                onWishlistClick = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }

        // Product Detail Screen
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) {
            ProductDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Cart Screen
        composable(route = Screen.Cart.route) {
            CartScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onCheckoutClick = {
                    navController.navigate(Screen.Checkout.route)
                }
            )
        }

        // Wishlist Screen
        composable(route = Screen.Wishlist.route) {
            WishlistScreen(
                onProductClick = { productId ->
                    navController.navigate(
                        Screen.ProductDetail.createRoute(productId)
                    )
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Checkout Screen
        composable(route = Screen.Checkout.route) {
            CheckoutScreen(
                onBackClick = { navController.popBackStack() },
                onOrderSuccess = {
                    navController.navigate(Screen.OrderSuccess.route) {
                        popUpTo(Screen.Cart.route) { inclusive = true }
                    }
                }
            )
        }

        // Order Success Screen
        composable(route = Screen.OrderSuccess.route) {
            OrderSuccessScreen(
                onContinueShopping = {
                    navController.navigate(Screen.ProductList.route) {
                        popUpTo(Screen.ProductList.route) { inclusive = true }
                    }
                }
            )
        }

        // Store Locator Screen
        composable(route = Screen.StoreLocator.route) {
            StoreLocatorScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // Profile Screen
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onBackClick = { navController.popBackStack() },
                onStoreLocatorClick = { navController.navigate(Screen.StoreLocator.route) },
                onLogoutSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}


