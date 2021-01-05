package com.scott.blog.model

import com.fasterxml.jackson.annotation.JsonTypeName
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("blank")
@JsonTypeName("blank")
class Blank(author: User) : Post(author)
