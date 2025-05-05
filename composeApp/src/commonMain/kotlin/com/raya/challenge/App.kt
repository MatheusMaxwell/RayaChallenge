package com.raya.challenge

import androidx.compose.runtime.*
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.raya.challenge.designsystem.component.loading.component.LoadingDialogComponent
import com.raya.challenge.designsystem.component.loading.provider.LocalLoadingDialog
import com.raya.challenge.designsystem.theme.RayaTheme
import com.raya.challenge.navgraph.RayaNavGraph
import okio.FileSystem
import org.jetbrains.compose.reload.DevelopmentEntryPoint
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    val loadingManager = LocalLoadingDialog.current

    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    DevelopmentEntryPoint {
        RayaTheme {
            KoinContext {
                LoadingDialogComponent(loadingManager)
                RayaNavGraph()
            }
        }
    }
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.crossfade(true).logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(1024L * 1024 * 1024) // 512MB
        .build()
}