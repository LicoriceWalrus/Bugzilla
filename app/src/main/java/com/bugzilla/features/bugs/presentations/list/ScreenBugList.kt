package com.bugzilla.features.bugs.presentations.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.sources.components.LoadingBox
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.getViewModel

@Composable
fun ScreenBugList(
    vm: BugListViewModel = getViewModel()
) {
    BugList(
        state = vm.screenState().collectAsState(),
        onBugClick = vm::changeInfoVisibility
    )
}

@Composable
private fun BugList(
    state: State<BugListScreenState>,
    onBugClick: (Bug) -> Unit
) {
    LoadingBox(
        modifier = Modifier.fillMaxSize(),
        loading = state.value.loading
    ) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val bugs = state.value.bugs
            items(
                count = bugs.size,
                key = { position: Int -> bugs[position].id },
            ) { position ->
                BugItem(bug = bugs[position], onItemClick = { onBugClick(bugs[position]) })
            }
        }
    }
}

@Composable
@Preview
private fun BugListPreview() {
    BugList(
        state = flowOf<BugListScreenState>().collectAsState(
            initial = BugListScreenState(
                bugs = listOf(
                    Bug(
                        id = "35",
                        summary = "Краткое описание",
                        alias = "Name"
                    )
                )
            )
        ),
        onBugClick = {}
    )
}