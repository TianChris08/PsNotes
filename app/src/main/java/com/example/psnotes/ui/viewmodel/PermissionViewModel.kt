package com.example.psnotes.ui.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PermissionViewModel : ViewModel() {
    private val _locationPermissionGranted = MutableLiveData(false)
    val locationPermissionGranted: LiveData<Boolean> = _locationPermissionGranted

    private val _storagePermissionGranted = MutableLiveData(false)
    val storagePermissionGranted: LiveData<Boolean> = _storagePermissionGranted

    private val _internetPermissionGranted = MutableLiveData(true)
    val internetPermissionGranted: LiveData<Boolean> = _internetPermissionGranted

    fun checkLocationPermission(context: Context) {
        val permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        _locationPermissionGranted.value = permissionStatus == PackageManager.PERMISSION_GRANTED

        if (!locationPermissionGranted.value!!) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
        }
    }

    fun checkStoragePermission(context: Context) {
        val permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        _storagePermissionGranted.value = permissionStatus == PackageManager.PERMISSION_GRANTED

        if (!storagePermissionGranted.value!!) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101)
        }
    }

    fun checkInternetPermission() {
        // Suponiendo que no necesitas pedir permisos explícitamente para internet.
        _internetPermissionGranted.value = true
    }

    // Nuevos métodos para solicitar permisos desde PermissionScreen
    fun requestLocationPermission(context: Context) {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
    }

    fun requestStoragePermission(context: Context) {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101)
    }
}
