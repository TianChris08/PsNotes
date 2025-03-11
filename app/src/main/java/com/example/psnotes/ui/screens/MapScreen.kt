package com.example.psnotes.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.maps.android.compose.GoogleMap


@Composable
fun MapScreen(paddingValues: PaddingValues, navController: NavController) {
    GoogleMap(modifier = Modifier.padding(paddingValues))

}