package daniel.brian.todo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import daniel.brian.todo.model.Post

@Composable
fun PostComponent(
    modifier: Modifier = Modifier,
    post: Post,
    onMoreVertClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onMoreVertClick()
            } ){
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
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

@Composable
fun CreatePost(
    modifier: Modifier = Modifier,
    post: Post,
    onDismiss: () -> Unit,
    onConfirm: (Post) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(post) }) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Create Post") },
        text = {
            Column(
                modifier = modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                 OutlinedTextField(
                     value = post.title.toString(),
                     onValueChange = {  },
                     label = { Text("Title") },
                     keyboardOptions = KeyboardOptions.Default.copy(
                         keyboardType = KeyboardType.Text,
                         imeAction = ImeAction.Next
                     )
                 )
                 OutlinedTextField(
                     value = post.description.toString(),
                     onValueChange = { },
                     label = { Text("Description") },
                     keyboardOptions = KeyboardOptions.Default.copy(
                         keyboardType = KeyboardType.Text,
                         imeAction = ImeAction.Next
                     )
                 )
            }
        },
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.surface)
    )
}
