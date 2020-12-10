package com.scott.blog.model

import org.hibernate.query.criteria.internal.expression.function.CurrentTimestampFunction
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.STRING)
abstract class Post(
    @Id @GeneratedValue open val id: Long = -1,

    @ManyToOne
    open var author: User = User(),

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    open val date: Date = Date()
)