package com.bugzilla.features.bugs.presentations.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugzilla.R
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
        onBugClick = vm::changeInfoVisibility,
        onQueryChanged = vm::onQueryChanged,
        searchBugs = vm::searchBugs
    )
}

@Composable
private fun BugList(
    state: State<BugListScreenState>,
    onBugClick: (Bug) -> Unit,
    onQueryChanged: (String) -> Unit,
    searchBugs: () -> Unit
) {
    LoadingBox(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        loading = state.value.loading,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                state.value.bugs.let {
                    if (it.isEmpty()) {
                        Text(
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp)
                                .align(Center),
                            text = state.value.message
                                ?: stringResource(id = R.string.upload_bugs_empty),
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(top = 8.dp)
                        ) {
                            val bugs = state.value.bugs
                            items(
                                count = bugs.size,
                                key = { position: Int -> bugs[position].id },
                            ) { position ->
                                BugItem(
                                    bug = bugs[position],
                                    onItemClick = { onBugClick(bugs[position]) })
                            }
                        }
                    }
                }
            }
            TextField(
                singleLine = true,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                value = state.value.query ?: "",
                onValueChange = onQueryChanged,
                label = {
                    Text(text = stringResource(id = R.string.bug_search_hint))
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        searchBugs()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            searchBugs()
                        },
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Composable
@Preview
private fun EmptyBugListPreview() {
    BugList(
        state = flowOf<BugListScreenState>().collectAsState(
            initial = BugListScreenState(
                bugs = emptyList()
            )
        ),
        onBugClick = {},
        onQueryChanged = {},
        searchBugs = {}
    )
}

@Composable
@Preview
private fun BugListPreview() {
    BugList(
        state = flowOf<BugListScreenState>().collectAsState(
            initial = BugListScreenState(
                bugs = listOf(
                    Bug(
                        id = "65",
                        summary = "Краткое описание",
                        alias = "Name",
                        creator ="Vasiliy from Leningrad",
                        status ="Open",
                        severity ="Hot"
                    )
                )
            )
        ),
        onBugClick = {},
        onQueryChanged = {},
        searchBugs = {}
    )
}