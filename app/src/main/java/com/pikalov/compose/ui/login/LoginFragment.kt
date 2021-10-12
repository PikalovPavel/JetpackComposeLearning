package com.pikalov.compose.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.pikalov.compose.R
import com.pikalov.compose.ui.theme.BaseThemingFragment
import com.pikalov.compose.ui.theme.JetpackComposeLearningTheme
import com.pikalov.compose.util.navigateSafe
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseThemingFragment(), NavigateToContent {

    private val loginViewModel: LoginViewModel by viewModels()

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
                    LoginContent {
                        loginViewModel.login(requireActivity())
                    }
                }
            }
        }
    }

    override fun navigate() {
        navigateSafe(LoginFragmentDirections.loginToGallery())
    }
}