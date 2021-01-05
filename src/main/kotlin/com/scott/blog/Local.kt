package com.scott.blog

import com.scott.blog.model.Post

class Local : PostPublisher {
    override fun printTitleToConsole(post: Post) {
        println("Local: ${post.title}")
    }
}