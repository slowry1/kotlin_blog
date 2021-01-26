package com.scott.blog.model

import com.scott.blog.PublicationTypeCreds
import com.scott.blog.RoleType
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*
import javax.persistence.JoinColumn

import javax.persistence.JoinTable

import javax.persistence.ManyToMany


@Entity
data class Role (
    @Enumerated(EnumType.STRING)
    val rolename : RoleType,

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    val users: List<User> = listOf()

) : GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    override fun getAuthority(): String {
        return rolename.toString()
    }


}