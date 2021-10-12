package com.pikalov.compose.ui.imageDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.pikalov.compose.R
import com.pikalov.compose.ui.theme.JetpackComposeLearningTheme
import com.pikalov.compose.ui.theme.toast
import androidx.navigation.findNavController
import com.pikalov.compose.ui.theme.BaseThemingFragment
import com.pikalov.compose.util.share



class ImageDetailFragment : BaseThemingFragment() {

    private val imageDetailViewModel: ImageDetailViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // In order for savedState to work, the same ID needs to be used for all instances.
            id = R.id.image_detail_fragment
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                val uiState by imageDetailViewModel.uiState.collectAsState()
                JetpackComposeLearningTheme(theme.collectAsState().value) {
                    ImageDetailContent(
                        state = uiState,
                        onBackPressed = {
                            findNavController().popBackStack()
                        },
                        onShareClick = {
                            uiState.currentPhoto.urlBig
                                ?.let { requireActivity().share(it) } ?: toast(requireContext())
                        },
                        onSmallImageClick = { id: Int ->
                            imageDetailViewModel.changeUiState(id)
                        }
                    )
                }
            }
        }
    }
}


