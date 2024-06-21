package com.example.permissionhandling.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {onDismiss },
        dismissButton = {
            onDismiss
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider()
                Text(text =
                if (isPermanentlyDeclined) {
                    "Grant permission"
                }
                else{
                    "Ok"
                },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp))
            }
        },

        title = {
            Text(text = "Permission required")
        },
        modifier = modifier,
        text = {
          Text(
              text = permissionTextProvider.getDescription(isPermanentlyDeclined),
        )
        })
}


interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you have permanently declined camera permission. " +
                    "You can go to the app settings to enable it."
        }
        else {
            "This app needs access to your camera so that it can take pictures."
        }

    }
}
class RecordAudioPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you have permanently declined microphone permission. " +
                    "You can go to the app settings to enable it."
        }
        else {
            "This app needs access to your microphone so that your friends can hear you."
        }

    }
}
class PhoneCallPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you have permanently declined phone calling permission. " +
                    "You can go to the app settings to enable it."
        }
        else {
            "This app needs access to your calling permission so that "+
                    "you can call your friends."
        }

    }
}