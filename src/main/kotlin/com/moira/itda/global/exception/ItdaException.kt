package com.moira.itda.global.exception

class ItdaException(val errorCode: ErrorCode) : RuntimeException(errorCode.message) {
}