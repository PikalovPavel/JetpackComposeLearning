package com.pikalov.compose.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.pikalov.compose.R
import com.pikalov.compose.features.toPhotos
import com.pikalov.compose.ui.theme.JetpackComposeLearningTheme
import com.pikalov.compose.util.Result
import com.pikalov.compose.util.navigateSafe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    val galleryViewModel: GalleryViewModel by viewModels()

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
                JetpackComposeLearningTheme {
                    val uiState by galleryViewModel.uiState.collectAsState()
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = stringResource(id = R.string.app_title))
                                },
                                navigationIcon = null,
                                actions = {
                                    ClickableText(
                                        text = AnnotatedString(stringResource(id = R.string.logout)),
                                        modifier = Modifier.padding(end = 12.dp),
                                        style = MaterialTheme.typography.body2,
                                        onClick = {
                                            galleryViewModel.logout()
                                            navigateSafe(GalleryFragmentDirections.globalToLogin())
                                        }
                                    )
                                },
                                backgroundColor = Color.White
                            )
                        }
                    ) {
                        GalleryContent(state = uiState) { photo ->
                            if (uiState !is Result.Success) return@GalleryContent
                            val state = (uiState as Result.Success<GalleryUiState>)
                            navigateSafe(
                                GalleryFragmentDirections.galleryFragmentToImageDetailFragment(
                                    photo,
                                    state.data.photos.toPhotos()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}