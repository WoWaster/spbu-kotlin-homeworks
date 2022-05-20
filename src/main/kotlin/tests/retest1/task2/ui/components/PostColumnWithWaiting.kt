package tests.retest1.task2.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import tests.retest1.task2.models.Post

@Composable
fun PostColumnWithWaiting(posts: List<Post>){
    if (posts.isEmpty()) {
        CircularProgressIndicator()
    } else {
        PostColumn(posts)
    }
}
