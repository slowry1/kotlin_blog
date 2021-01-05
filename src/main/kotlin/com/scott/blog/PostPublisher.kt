package com.scott.blog

import com.scott.blog.model.Post

interface PostPublisher {
    fun printTitleToConsole(post: Post)

}