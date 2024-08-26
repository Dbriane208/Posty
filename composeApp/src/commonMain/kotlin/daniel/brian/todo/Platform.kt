package daniel.brian.todo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform