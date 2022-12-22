package com.grayseal.forecastapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grayseal.forecastapp.screens.*
import com.grayseal.forecastapp.screens.main.MainViewModel
import com.grayseal.forecastapp.screens.main.WeatherScreen

@Composable
fun WeatherNavigation(){
    val navController = rememberNavController()
    val mainViewModel = hiltViewModel<MainViewModel>()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(WeatherScreens.WeatherScreen.name){
            WeatherScreen(navController = navController, mainViewModel, lat = -1.2369159, lon = 36.8911082)
        }
        composable(WeatherScreens.ForecastScreen.name){
            ForecastScreen(navController = navController, mainViewModel)
        }
        composable(WeatherScreens.LocationScreen.name){
            LocationScreen(navController = navController, mainViewModel)
        }
        composable(WeatherScreens.SettingScreen.name){
            SettingScreen(navController = navController)
        }
    }
}