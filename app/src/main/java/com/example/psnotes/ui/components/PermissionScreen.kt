package com.example.psnotes.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.psnotes.ui.viewmodel.PermissionViewModel


@Composable
fun PermissionScreen(permissionViewModel: PermissionViewModel) {
    val context = LocalContext.current

    // Verificar si ya se ha concedido algún permiso
    val locationPermissionGranted by permissionViewModel.locationPermissionGranted.observeAsState(
        false
    )
    val storagePermissionGranted by permissionViewModel.storagePermissionGranted.observeAsState(
        false
    )

    SideEffect {
        if (!locationPermissionGranted) {
            permissionViewModel.requestLocationPermission(context)
        } else if (!storagePermissionGranted) {
            permissionViewModel.requestStoragePermission(context)
        }
    }

}
