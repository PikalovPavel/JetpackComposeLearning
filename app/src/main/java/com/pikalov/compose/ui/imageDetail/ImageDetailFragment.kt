package com.pikalov.compose.ui.imageDetail

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.pikalov.compose.R
import com.pikalov.compose.features.Photo
import com.pikalov.compose.ui.theme.JetpackComposeLearningTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import android.os.Environment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.pikalov.compose.ui.theme.toast
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

import java.io.FileOutputStream
import android.content.pm.PackageManager

import android.app.Activity
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import java.io.OutputStream


data class ImageDetailUiState(
    val currentPhoto: Photo,
    val otherPhotos: List<Photo>
)

@AndroidEntryPoint
class ImageDetailFragment : Fragment() {


    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // In order for savedState to work, the same ID needs to be used for all instances.
            id = R.id.login_fragment
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )


            setContent {
                val args = ImageDetailFragmentArgs.fromBundle(requireArguments())
                val uiState = ImageDetailUiState(args.currentPhoto, args.otherPhotos.photos)
                JetpackComposeLearningTheme {
                    ImageDetailContent(
                        state = uiState,
                        onBackPressed = {
                            findNavController().popBackStack()
                        }
                    ) {
                        lifecycleScope.launch {
                            kotlin.runCatching {
                                val loader = ImageLoader(requireContext())
                                val request = ImageRequest.Builder(requireContext())
                                    .data(uiState.currentPhoto.urlSmall)
                                    .allowHardware(false) // Disable hardware bitmaps.
                                    .build()

                                val result = (loader.execute(request) as SuccessResult).drawable
                                val bitmap = (result as BitmapDrawable).bitmap
                                saveBitmap(bitmap = bitmap)
                            }.onFailure {
                                toast(requireContext()).show()
                                Timber.wtf(it)
                            }
                        }
                    }
                }
            }
        }
    }

    //TODO(remove)
    private fun saveBitmap(bitmap: Bitmap) {
        if (verifyStoragePermissions(requireActivity())) {
            saveMediaToStorage(bitmap)
        }
    }

    fun saveMediaToStorage(bitmap: Bitmap) {
        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            context?.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            toast(requireContext(), R.string.success).show()
        }
    }
}


private fun verifyStoragePermissions(activity: Activity) : Boolean {
    // Storage Permissions
    // Storage Permissions
    val REQUEST_EXTERNAL_STORAGE = 1
    val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    // Check if we have write permission
    val permission =
        ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    return if (permission != PackageManager.PERMISSION_GRANTED) {
        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
            activity,
            PERMISSIONS_STORAGE,
            REQUEST_EXTERNAL_STORAGE
        )
        false
    } else {
        true
    }
}
