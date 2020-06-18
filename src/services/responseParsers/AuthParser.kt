package com.medtracker.services.responseParsers

import com.medtracker.services.dto.AuthDTO

class AuthParser {
    fun parse(jwtToken: String): AuthDTO {
        return AuthDTO(jwtToken)
    }
}