package com.moira.itda.global.auth.aop

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
annotation class IsAdmin()
