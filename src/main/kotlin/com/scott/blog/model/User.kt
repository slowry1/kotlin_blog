package com.scott.blog.model

import com.scott.blog.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

// Entities are POJOs representing data that can be persisted to the database.
// An Entity represents a table stored in a database.
@Entity
@Table(name = "users")
class User (
    val name: String = "",
    val email: String = "",
    val pass: String = "",
    @ManyToMany(fetch = FetchType.EAGER)
    val roles: List<Role> = listOf()
) : UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0;

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        var grantedAuthorities : MutableCollection<GrantedAuthority> = ArrayList()
        for (role in roles) {
            when (role.authority) {
                RoleType.Editor.toString() -> grantedAuthorities.add((SimpleGrantedAuthority("ROLE_Editor")))
                RoleType.Author.toString() -> grantedAuthorities.add((SimpleGrantedAuthority("ROLE_Author")))
            }
        }
        return grantedAuthorities
    }

    override fun getPassword(): String {
        return pass
    }

    override fun getUsername(): String {
        return name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}