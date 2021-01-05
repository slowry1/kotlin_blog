package com.scott.blog.service

import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class PostService {
    fun rewindTimeByHours(hours: Long) : LocalDateTime {
        return LocalDateTime.now().minusHours(hours)
    }
}