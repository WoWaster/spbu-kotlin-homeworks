package tests.retest1.task2.ui.components

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tests.retest1.task2.models.Post

const val SCREEN_FRACTION = 0.75F

@Composable
fun PostColumn(posts: List<Post>) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        val state = rememberLazyListState()

        LazyColumn(
            modifier = Modifier.fillMaxWidth(SCREEN_FRACTION),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = state
        ) {
            items(posts) {
                Post(it)
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = state)
        )
    }
}
