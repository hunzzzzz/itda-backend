package com.moira.itda.domain.account.signup.component

import java.security.SecureRandom

object IdentifyCodeGenerator {
    /**
     * 6자리 랜덤 코드 생성
     */
    fun generate(): String {
        val secureRandom = SecureRandom()
        val code = secureRandom.nextInt(1_000_000)

        return "%06d".format(code)
    }
}