package com.moira.itda.domain.health_check

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    /**
     * Health Check API Endpoint
     */
    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<Nothing?> = ResponseEntity.ok().body(null)
}