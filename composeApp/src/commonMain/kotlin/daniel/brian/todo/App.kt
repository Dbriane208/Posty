package daniel.brian.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import daniel.brian.todo.components.PostComponent
import daniel.brian.todo.viewmodel.PostViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.verticalScroll(rememberScrollState())
                .fillMaxSize(),
        ) {

            val postViewModel = getViewModel(Unit, viewModelFactory { PostViewModel() })
            val uiState by postViewModel.uiState.collectAsState()

            LaunchedEffect(postViewModel){
                postViewModel.updatePosts()
            }

            Column {
                uiState.posts.forEach{post ->
                    PostComponent(
                        modifier = Modifier,
                        post =post
                    )
                }
            }
        }

    }
}