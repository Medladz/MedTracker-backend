package com.medtracker.services

import com.medtracker.models.User
import com.medtracker.repositories.UserRepository


class UserService {

    fun getAllVerifiedUserIds(): ArrayList<Int>{
        val userRepository = UserRepository()
        return userRepository.getAllVerifiedUserIds()
    }

}