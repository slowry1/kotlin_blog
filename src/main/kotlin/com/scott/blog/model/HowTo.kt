package com.scott.blog.model

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("how_to")
class HowTo(
    val tutorial: String = ""
) : Post()
