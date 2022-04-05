package com.bugzilla.sources.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugzilla.R

@Composable
fun BugToolbar(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.White,
        elevation = 8.dp,
        content = content
    )
}

@Composable
@Preview
fun BugToolbarPreview() {
    BugToolbar(
        content = {
            Icon(painter = painterResource(id = R.drawable.ic_filter), contentDescription = null)
        }
    )
}