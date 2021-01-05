package com.scott.blog

import com.scott.blog.model.Post

class Facebook : PostPublisher {
    override fun printTitleToConsole(post: Post) {
        println("Facebook: ${post.title}")
    }
}