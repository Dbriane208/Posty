package daniel.brian.todo.repository

import daniel.brian.todo.model.Post
import daniel.brian.todo.model.PostBody
import daniel.brian.todo.network.KtorClient
import daniel.brian.todo.utils.NetworkResult
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PostRepository {
   private val client = KtorClient()

    suspend fun getPosts(): NetworkResult<List<Post>> =
        try {
            val response: List<Post> = client.httpClient
                .get("https://jsonplaceholder.typicode.com/posts")
                .body()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message.toString())
        }

    suspend fun createPost(title: String, description: String): NetworkResult<Post> =
        try {
            val post = Post(
                title = title,
                description = description,
                userId = 7,
                postId = 1
            )

            val response: Post = client.httpClient
                .post("https://jsonplaceholder.typicode.com/posts") {
                    contentType(ContentType.Application.Json)
                    setBody(post)
                }
                .body()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message.toString())
        }

    suspend fun updatePost(postId: String, title: String, description: String): NetworkResult<Post>  =
       try {
           val post = Post(
               title = title,
               description = description,
               userId = 7,
               postId = 1
           )

           val response: Post = client.httpClient
               .put("https://jsonplaceholder.typicode.com/posts/$postId"){
                   contentType(ContentType.Application.Json)
                   setBody(post)
               }
               .body()
           NetworkResult.Success(response)
       } catch (e: Exception) {
           NetworkResult.Error(e.message.toString())
       }

}