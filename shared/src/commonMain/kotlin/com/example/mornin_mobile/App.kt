package com.example.mornin_mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mornin_mobile.data.local.TokenProvider
import com.example.mornin_mobile.data.local.TokenStorage
import com.example.mornin_mobile.data.remote.AuthRepositoryImpl
import com.example.mornin_mobile.data.remote.DigestRepositoryImpl
import com.example.mornin_mobile.data.remote.HttpClientFactory
import com.example.mornin_mobile.data.remote.TopicsRepositoryImpl
import com.example.mornin_mobile.domain.GetDigestForToday
import com.example.mornin_mobile.domain.RequestOtpUseCase
import com.example.mornin_mobile.domain.SaveTokenUseCase
import com.example.mornin_mobile.domain.VerifyOtpUseCase
import com.example.mornin_mobile.domain.topics.CreateTopicUseCase
import com.example.mornin_mobile.domain.topics.DeleteTopicUseCase
import com.example.mornin_mobile.domain.topics.GetTopicsUseCase
import com.example.mornin_mobile.features.auth.AuthScreen
import com.example.mornin_mobile.features.auth.AuthViewModel
import com.example.mornin_mobile.features.digest.MorninViewModel
import com.example.mornin_mobile.features.topics.TopicsViewModel

private const val BASE_URL = "https://mornin-digest-staging.up.railway.app"

@Composable
fun App(tokenStorage: TokenStorage) {
    val tokenProvider = remember { TokenProvider(tokenStorage) }
    val httpClient = remember { HttpClientFactory.create(tokenProvider) }
    val saveTokenUseCase = remember { SaveTokenUseCase(tokenProvider) }

    val startDestination: AppRoute =
        if (tokenProvider.getToken() != null) AppRoute.Mornin else AppRoute.Auth

    val navController = rememberNavController()

    MaterialTheme {
        NavHost(navController = navController, startDestination = startDestination) {

            composable<AppRoute.Auth> {
                val authViewModel: AuthViewModel = viewModel(
                    factory = AuthViewModel.factory(
                        requestOtpUseCase = RequestOtpUseCase(AuthRepositoryImpl(httpClient, BASE_URL)),
                        verifyOtpUseCase = VerifyOtpUseCase(AuthRepositoryImpl(httpClient, BASE_URL)),
                        saveTokenUseCase = saveTokenUseCase
                    )
                )

                val authState by authViewModel.state.collectAsStateWithLifecycle()
                LaunchedEffect(authState) {
                    if (authState is AuthViewModel.AuthState.Authenticated) {
                        navController.navigate(AppRoute.Mornin) {
                            popUpTo(AppRoute.Auth) { inclusive = true }
                        }
                    }
                }

                AuthScreen(viewModel = authViewModel)
            }

            composable<AppRoute.Mornin> {
                val topicsRepo = remember { TopicsRepositoryImpl(httpClient, BASE_URL) }

                val topicsViewModel: TopicsViewModel = viewModel(
                    factory = TopicsViewModel.factory(
                        getTopicsUseCase = GetTopicsUseCase(topicsRepo),
                        createTopicUseCase = CreateTopicUseCase(topicsRepo),
                        deleteTopicUseCase = DeleteTopicUseCase(topicsRepo)
                    )
                )

                val morninViewModel: MorninViewModel = viewModel(
                    factory = MorninViewModel.factory(
                        GetDigestForToday(DigestRepositoryImpl(httpClient, BASE_URL))
                    )
                )

                Scaffold(
                    topBar = {
                        Text(
                            text = "Mornin'",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                ) { innerPadding ->
                    MorninScreen(
                        topicsViewModel = topicsViewModel,
                        morninViewModel = morninViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
