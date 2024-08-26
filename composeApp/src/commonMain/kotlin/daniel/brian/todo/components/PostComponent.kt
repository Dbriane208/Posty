package daniel.brian.todo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import daniel.brian.todo.model.Post

@Composable
fun PostComponent(
    modifier: Modifier = Modifier,
    post: Post
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            InfoRow("User ID:", post.userId?.toString() ?: "N/A")
            InfoRow("Post ID:", post.postId?.toString() ?: "N/A")
            InfoRow("Title:", post.title ?: "N/A", isMultiLine = true)
            InfoRow("Description:", post.description ?: "N/A", isMultiLine = true)
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    isMultiLine: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = if (isMultiLine) Alignment.Top else Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            maxLines = if (isMultiLine) Int.MAX_VALUE else 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
