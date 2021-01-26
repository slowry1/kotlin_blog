package com.scott.blog

import com.scott.blog.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

// TODO This is not used. this is when i was going down the rabbit hole and i went a simpler route instead.
//class MyUserPrincipal(user: User) : UserDetails {
//    private var user : User = user
//
//    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//        var authorities : List<GrantedAuthority> = ArrayList()
//
//
//
//        TODO("Not yet implemented")
//    }
//
//    override fun getPassword(): String {
//        TODO("Not yet implemented")
//    }
//
//    override fun getUsername(): String {
//        TODO("Not yet implemented")
//    }
//
//    override fun isAccountNonExpired(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isEnabled(): Boolean {
//        TODO("Not yet implemented")
//    }
////    constructor() MyUserPrincipal(user: User) {
////        this.user = user
////    }
//}