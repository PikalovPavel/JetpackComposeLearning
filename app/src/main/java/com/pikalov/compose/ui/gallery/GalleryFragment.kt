package com.pikalov.compose.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.pikalov.compose.R
import com.pikalov.compose.features.toPhotos
import com.pikalov.compose.ui.theme.BaseThemingFragment
import com.pikalov.compose.ui.theme.JetpackComposeLearningTheme
import com.pikalov.compose.util.Result
import com.pikalov.compose.util.navigateSafe

class GalleryFragment : BaseThemingFragment() {

    private val galleryViewModel: GalleryViewModel by viewModels()

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
                JetpackComposeLearningTheme(theme.collectAsState().value) {
                    val uiState by galleryViewModel.uiState.collectAsState()
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.app_title),
                                        color = MaterialTheme.colors.primary
                                    )
                                },
                                navigationIcon = null,
                                actions = {
                                    Row() {
                                        Image(
                                            painter = painterResource(R.drawable.ic_change_theme),
                                            contentDescription = null,
                                            Modifier
                                                .padding(end = 12.dp)
                                                .clickable {
                                                    galleryViewModel.changeTheme()
                                                },
                                            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                                        )
                                        ClickableText(
                                            text = AnnotatedString(stringResource(id = R.string.logout)),
                                            modifier = Modifier.padding(end = 12.dp),
                                            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary),
                                            onClick = {
                                                galleryViewModel.logout()
                                                navigateSafe(GalleryFragmentDirections.globalToLogin())
                                            }
                                        )
                                    }
                                },
                                backgroundColor = MaterialTheme.colors.secondary
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