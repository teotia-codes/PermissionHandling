package com.example.permissionhandling

import android.os.Bundle
import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.permissionhandling.dialog.CameraPermissionTextProvider
import com.example.permissionhandling.dialog.PermissionDialog
import com.example.permissionhandling.dialog.PhoneCallPermissionTextProvider
import com.example.permissionhandling.dialog.RecordAudioPermissionTextProvider
import com.example.permissionhandling.ui.theme.PermissionHandlingTheme

class MainActivity : ComponentActivity() {
    //    private val permissionToRequest = arrayOf(
//        Manifest.permission.CAMERA,
//        Manifest.permission.RECORD_AUDIO,
//        Manifest.permission.CALL_PHONE,
//    )
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionHandlingTheme {
//                val viewModel = viewModel<MainViewModel>()
//                val dialogQueue = viewModel.visiblePermissionDialogQueue
//
//                val cameraPermission =
//                    rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
//                        viewModel.onPermissionResult(
//                            permission = Manifest.permission.CAMERA,
//                            isGranted = isGranted
//                        )
//
//                    }
//                val multiplePermissionResultLauncher =
//                    rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { perms ->
//                        permissionToRequest.forEach { permission ->
//                            viewModel.onPermissionResult(
//                                permission = Manifest.permission.CAMERA,
//                                isGranted = perms[permission] == true
//                            )
//                        }
//
//
//                    }
                val context = LocalContext.current
                var hasNotificationPermission by remember {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                }
                val permissionLauncher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission())
                    { isGranted ->
                        hasNotificationPermission = isGranted

                    }


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        Button(onClick = {

                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }) {
                            Text(text = "Request notification")
                        }
                        Button(onClick = {
                            if (hasNotificationPermission) {
                                showNotification()
                            }


                        }) {
                            Text(text = "Show notification")
                        }

                    }


                    //                    Column(
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        modifier = Modifier.padding(innerPadding)
//                    ) {
//                        Button(onClick = {
//                            cameraPermission.launch(
//                                Manifest.permission.CAMERA
//                            )
//
//                        }) {
//                            Text(text = "Request one permission")
//                        }
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Button(onClick = {
//                            multiplePermissionResultLauncher.launch(
//                                arrayOf(
//                                    Manifest.permission.CAMERA,
//                                    Manifest.permission.RECORD_AUDIO,
//                                    Manifest.permission.CALL_PHONE,
//                                )
//                            )
//                        }) {
//                            Text(text = "Request multiple permission")
//                        }
//                    }
//                    dialogQueue
//                        .reversed()
//                        .forEach {
//                            PermissionDialog(
//                                permissionTextProvider = when (it) {
//                                    Manifest.permission.CAMERA -> {
//                                        CameraPermissionTextProvider()
//                                    }
//
//                                    Manifest.permission.RECORD_AUDIO -> {
//                                        RecordAudioPermissionTextProvider()
//                                    }
//
//                                    Manifest.permission.CALL_PHONE -> {
//                                        PhoneCallPermissionTextProvider()
//                                    }
//
//                                    else -> return@forEach
//                                },
//                                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(it),
//                                onDismiss = { viewModel::dismissDialog },
//                                onOkClick = {
//                                    viewModel.dismissDialog()
//                                    multiplePermissionResultLauncher.launch(
//                                        arrayOf(it)
//                                    )
//                                },
//                                onGoToAppSettingsClick = ::openAppSettings
//                            )
//                        }
                }
            }
        }
    }

    private fun showNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, "channel_id")
            .setContentText("This is a notification")
            .setContentTitle("Notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
        notificationManager.notify(1, notification)
    }
}

//fun Activity.openAppSettings() {
//    Intent(
//        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//        Uri.fromParts("package", packageName, null)
//    ).also(::startActivity)
//
//}


