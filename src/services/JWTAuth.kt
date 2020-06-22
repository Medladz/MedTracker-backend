package com.medtracker.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.medtracker.models.User

object JWTAuth {
    private const val secret = "UjXn2r5u8x/A?D(G-KaPdSgVkYp3s6v9"
    private const val issuer = "com.medtracker"
    private val algorithm = Algorithm.HMAC512(secret)

    const val realm = "com.medtracker"

    val verifier: JWTVerifier = JWT.require(algorithm).withIssuer(issuer).build()

    fun generate(user: User): String = JWT.create()
        .withIssuer(issuer)
        .withClaim("userId", user.id)
        .sign(algorithm)
}