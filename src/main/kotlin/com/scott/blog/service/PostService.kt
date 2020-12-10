package com.scott.blog.service

import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class PostService {
    fun rewindTimeByHours(hours: Long) : Timestamp {
        return Timestamp(System.currentTimeMillis() - (hours*60*60*1000))
    }
}