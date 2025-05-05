package com.raya.challenge

import androidx.compose.ui.window.ComposeUIViewController
import com.raya.challenge.di.appModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        startKoin {
            modules(appModule)
        }
    }
) { App() }