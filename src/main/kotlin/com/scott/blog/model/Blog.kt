package com.scott.blog.model

import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("blog")
class Blog(
    val dailyActivity: String = ""
) : Post()
