package com.bugzilla.sources.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugzilla.R

@Composable
fun BugToolbar(
    title: String,
    actions: (@Composable RowScope.() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                maxLines = 1,
                text = title,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp),
                fontSize = 16.sp,
            )
        },
        backgroundColor = Color.White,
        actions = actions ?: {}
    )
}

@Composable
@Preview
fun BugToolbarPreview() {
    BugToolbar(
        title = "Bugs list",
        actions = {
            Icon(painter = painterResource(id = R.drawable.ic_filter), contentDescription = null)
        }
    )
}