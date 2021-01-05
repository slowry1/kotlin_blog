package com.scott.blog.model

import com.fasterxml.jackson.annotation.JsonTypeName
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("how_to")
@JsonTypeName("how_to")
class HowTo(
    val tutorial: String = "", author: User
) : Post(author)
