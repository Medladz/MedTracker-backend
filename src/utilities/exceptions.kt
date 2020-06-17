package com.medtracker.utilities

class UnauthorizedException(message: String? = null): Exception(message)
class BadRequestException(message: String? = null): Exception(message)
class UnprocessableEntityException(message: String? = null): Exception(message)
class InternalServerErrorException(message: String? = null): Exception(message)