package com.raya.challenge.balance.impl.presentation.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raya.challenge.designsystem.component.shimmer.shimmer
import com.raya.challenge.designsystem.theme.color.RayaColor
import com.raya.challenge.designsystem.theme.dimens.padding.Padding
import com.raya.challenge.designsystem.theme.dimens.size.CustomSize

@Composable
internal fun BalanceLoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize().padding(Padding.medium),
        verticalArrangement = Arrangement.spacedBy(Padding.medium)
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Card(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f).shimmer(),
            shape = RoundedCornerShape(CustomSize.cornerRadius),
            elevation = 0.dp
        ) {}

        LazyColumn {
            items(4) {
                SkeletonBalanceCard()
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f).shimmer(),
            shape = RoundedCornerShape(CustomSize.cornerRadius),
            elevation = 0.dp
        ) {}
    }
}

@Composable
private fun SkeletonBalanceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(top = Padding.medium)
            .shimmer(),
        shape = RoundedCornerShape(CustomSize.cornerRadius),
        elevation = 0.dp
    ) {}
}

@Preview
@Composable
private fun BalanceLoadingContentPreview() {
    BalanceLoadingContent()
}