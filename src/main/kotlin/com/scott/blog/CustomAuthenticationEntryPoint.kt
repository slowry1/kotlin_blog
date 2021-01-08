package com.scott.blog

import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class CustomAuthenticationEntryPoint : BasicAuthenticationEntryPoint() {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + " ")
        response?.status = HttpServletResponse.SC_UNAUTHORIZED
        response?.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        val writer = response?.writer
        writer?.println("HTTP Status 401 - " + authException?.message)
    }

    override fun afterPropertiesSet() {
        realmName = "Blog"
        super.afterPropertiesSet()
    }

}

