package com.scott.blog

import com.scott.blog.model.Post

class Twitter : PostPublisher {
    override fun printTitleToConsole(post: Post) {
        println("Twitter: ${post.title}")
    }
}