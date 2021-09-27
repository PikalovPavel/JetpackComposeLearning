package com.pikalov.compose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.pikalov.compose.features.AuthRepository
import com.pikalov.compose.ui.login.LoginViewModel
import com.pikalov.compose.ui.login.NavigateToContent
import com.pikalov.compose.ui.theme.toast
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.exceptions.VKAuthException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject




@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.host) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navController = navHostFragment.navController
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)

        lifecycleScope.launchWhenCreated {
            mainViewModel.navigateTo.collectLatest {
                when(it) {
                    MainViewModel.Navigate.IDLE -> {}
                    MainViewModel.Navigate.CONTENT -> {
                        navGraph.startDestination = R.id.gallery_fragment
                        navController.graph = navGraph
                        return@collectLatest
                    }
                    MainViewModel.Navigate.LOGIN -> {
                        navGraph.startDestination = R.id.login_fragment
                        navController.graph = navGraph
                        return@collectLatest
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {

            override fun onLogin(token: VKAccessToken) {
                mainViewModel.onLogin()
                navigateAfterLogin()
            }

            override fun onLoginFailed(authException: VKAuthException) {
                toast(this@MainActivity).show()
                Timber.wtf(authException)
            }
        }
        //SDK API, can not change deprecated
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    //Actually is no way to elegant navigate after login, because VK SDK API.
    private fun navigateAfterLogin() {
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.host) as NavHostFragment
        kotlin.runCatching {
            lifecycleScope.launch {
                delay(DELAY_NAVIGATE)
                (navHostFragment.childFragmentManager.fragments[0] as? NavigateToContent)?.navigate()
            }
        }.onFailure {
            Timber.wtf(it)
        }
    }

    companion object {
        const val DELAY_NAVIGATE = 200L
    }



}


