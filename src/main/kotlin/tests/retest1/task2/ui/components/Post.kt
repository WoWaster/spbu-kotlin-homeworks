package tests.retest1.task2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tests.retest1.task2.models.Post

const val SCREEN_FRACTION = 0.75F

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post(post: Post) {
    Column(
        Modifier.fillMaxWidth(SCREEN_FRACTION),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text("Цитата #${post.id}")
        }
        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                post.text,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Light
            )
        }
    }
}
