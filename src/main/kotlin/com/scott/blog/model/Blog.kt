package com.scott.blog.model

import com.fasterxml.jackson.annotation.JsonTypeName
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("blog")
@JsonTypeName("blog")
class Blog(
    val dailyActivity: String = "", author: User
) : Post(author)
