package com.scott.blog.model

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("blank")
class Blank() : Post()
