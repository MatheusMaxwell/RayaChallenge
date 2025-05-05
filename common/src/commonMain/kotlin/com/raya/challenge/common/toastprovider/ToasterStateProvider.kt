package com.raya.challenge.common.toastprovider

import androidx.compose.runtime.staticCompositionLocalOf
import com.dokar.sonner.ToasterState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val toasterStateProvider = ToasterState(
    coroutineScope = CoroutineScope(Dispatchers.Default),
)

/**
 * Provides a [Loading Dialog] that can be used by Easy application.
 */
val LocalToasterDialog = staticCompositionLocalOf {
    toasterStateProvider
}
