package daniel.brian.todo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import daniel.brian.todo.components.CreatePost
import daniel.brian.todo.components.PostComponent
import daniel.brian.todo.model.Post
import daniel.brian.todo.utils.UiEvents
import daniel.brian.todo.viewmodel.PostViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            PostScreen()
        }

    }
}

@Composable
fun PostScreen() {
    val postViewModel = getViewModel(Unit, viewModelFactory { PostViewModel() })
    val uiState by postViewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    var showCreateDialog by remember { mutableStateOf(false) }
    var selectedPost by remember { mutableStateOf<Post?>(null) }

    LaunchedEffect(postViewModel){
        postViewModel.getAllPosts()
    }

    LaunchedEffect(Unit){
        postViewModel.eventFlow.collect{ event ->
            when(event){
                is UiEvents.SnackBarEvent -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }

        }
    }

    LazyColumn {
        items(uiState.posts){post ->
            PostComponent(
                modifier = Modifier,
                post = post,
                onMoreVertClick = {
                    postViewModel.onPostSelected(post = post)
                }
            )
        }
    }


    if(uiState.selectedPost != null && uiState.showCreatePostDialog){
        CreatePost(
            post = uiState.selectedPost!!,
            onDismiss = {
                postViewModel.onDismissDialog()
            },
            onConfirm = { newPost->
                postViewModel.onPostConfirmed(newPost)
            }
        )
    }

}