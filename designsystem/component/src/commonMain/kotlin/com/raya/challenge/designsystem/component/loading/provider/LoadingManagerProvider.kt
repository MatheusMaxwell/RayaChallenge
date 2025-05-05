package com.raya.challenge.designsystem.component.loading.provider

import androidx.compose.runtime.staticCompositionLocalOf
import com.raya.challenge.designsystem.component.loading.LoadingDialogManager

/**
 * Provides a [Loading Dialog] that can be used by Easy application.
 */
val LocalLoadingDialog = staticCompositionLocalOf { LoadingDialogManager() }
