package com.vishnu.testapplication.compose.launch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.vishnu.testapplication.R

@Composable
fun LaunchView() {
    Surface(
        modifier = Modifier.fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_uob),
            null
        )
    }
}

@Preview(widthDp = 320, heightDp = 480)
@Composable
fun PreviewLaunchView() {
    MaterialTheme {
        LaunchView()
    }
}