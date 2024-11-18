package ru.urfu.consecutivepractice.presentation.profile.screen

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.threeten.bp.LocalTime
import ru.urfu.consecutivepractice.R
import ru.urfu.consecutivepractice.presentation.profile.viewModel.EditProfileViewModel
import java.io.File
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Parcelize
class EditProfileScreen(
    override val screenKey: ScreenKey = generateScreenKey(),
) : Screen {


    @Composable
    override fun Content(modifier: Modifier) {

        val navigation = LocalStackNavigation.current
        val context = LocalContext.current

        val viewModel = koinViewModel<EditProfileViewModel> { parametersOf(navigation) }
        val state = viewModel.viewState

        var imageUri by remember { mutableStateOf<Uri?>(null) }

        val pickMedia: ActivityResultLauncher<PickVisualMediaRequest> =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                viewModel.onImageSelected(uri)
            }

        val requestPermissionLauncher =
            rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { map: Map<String, Boolean> ->
                if (map.values.contains(false)) {
                    val dialog = AlertDialog.Builder(context)
                        .setMessage("Ну, так не пойдет...")
                        .setCancelable(false)
                        .setPositiveButton("OK") { _, _ ->
                            viewModel.onPermissionDenied()
                        }

                    dialog.show()
                }
                viewModel.onPermissionClosed()
            }

        val mGetContent = rememberLauncherForActivityResult<Uri, Boolean>(
            ActivityResultContracts.TakePicture()
        ) { success: Boolean ->
            if (success) {
                viewModel.onImageSelected(imageUri)
            }
        }

        Scaffold(
            contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.edit_profile))
                    },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            Modifier
                                .padding(end = 8.dp)
                                .clickable { viewModel.back() }
                        )
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null,
                            Modifier
                                .padding(end = 8.dp)
                                .clickable { viewModel.onDoneClicked() }
                        )
                    },
                    modifier = Modifier.shadow(elevation = 1.dp)
                )
            }) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = state.photoUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(128.dp)
                        .clip(CircleShape)
                        .clickable { viewModel.onAvatarClicked() },
                    error = painterResource(R.drawable.mobile_phone)
                )
                TextField(
                    value = state.name,
                    onValueChange = { viewModel.onNameChanged(it) },
                    label = { Text(stringResource(R.string.name)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                TextField(
                    value = state.url,
                    onValueChange = { viewModel.onUrlChanged(it) },
                    label = { Text(stringResource(R.string.link)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                TextField(
                    value = state.timeString,
                    onValueChange = { viewModel.onTimeChanged(it) },
                    label = { Text("Время любимой пары") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    isError = state.timeError != null,
                    trailingIcon = {
                        Icon(
                            painterResource(id = R.drawable.time),
                            null,
                            modifier = Modifier.clickable { viewModel.onTimeInputClicked() })
                    }
                )
                state.timeError?.let {
                    Text(
                        it,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
                if (state.isNeedToShowTimePicker) {
                    DialWithDialogExample(
                        onConfirm = { h, m -> viewModel.onTimeConfirmed(h, m) },
                        onDismiss = { viewModel.onTimeDialogDismiss() },
                        time = state.time
                    )
                }
            }
        }

        if (state.isNeedToShowPermission) {
            LaunchedEffect(Unit) {
                val permissions = mutableListOf<String>()
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }

                if (
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissions.add(Manifest.permission.POST_NOTIFICATIONS)
                }

                requestPermissionLauncher.launch(permissions.toTypedArray())
            }
        }

        fun onCameraSelected() {
            val baseDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            )
            val pictureFile = File(baseDir, "picture_${Date().time}.jpg")
            imageUri = FileProvider.getUriForFile(
                context,
                context.packageName + ".provider",
                pictureFile
            )
            imageUri?.let { mGetContent.launch(it) }
        }

        if (state.isNeedToShowSelect) {
            Dialog(onDismissRequest = { viewModel.onSelectDismiss() }) {
                Surface(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = stringResource(R.string.camera),
                            Modifier.clickable {
                                onCameraSelected()
                                viewModel.onSelectDismiss()
                            }
                        )
                        Text(text = stringResource(R.string.gallery),
                            Modifier.clickable {
                                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                viewModel.onSelectDismiss()
                            })
                    }
                }
            }
        }
    }


    @Composable
    fun DialWithDialogExample(
        onConfirm: (Int, Int) -> Unit,
        onDismiss: () -> Unit,
        time: LocalTime
    ) {
        val timePickerState = rememberTimePickerState(
            initialHour = time.hour,
            initialMinute = time.minute,
            is24Hour = true,
        )

        TimePickerDialog(
            onDismiss = { onDismiss() },
            onConfirm = { onConfirm(timePickerState.hour, timePickerState.minute) }
        ) {
            TimePicker(
                state = timePickerState,
            )
        }
    }

    @Composable
    fun TimePickerDialog(
        onDismiss: () -> Unit,
        onConfirm: () -> Unit,
        content: @Composable () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Отмена")
                }
            },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("OK")
                }
            },
            text = { content() }
        )
    }

}