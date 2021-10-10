package com.pikalov.compose.ui.imageDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pikalov.compose.R
import com.pikalov.compose.ui.theme.JetpackComposeLearningTheme
import dagger.hilt.android.AndroidEntryPoint
import com.pikalov.compose.ui.theme.toast
import androidx.navigation.findNavController
import com.pikalov.compose.util.share



@AndroidEntryPoint
class ImageDetailFragment : Fragment() {

    val imageDetailViewModel: ImageDetailViewModel by viewModels()

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
                val uiState by imageDetailViewModel.uiState.collectAsState()
                JetpackComposeLearningTheme {
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


