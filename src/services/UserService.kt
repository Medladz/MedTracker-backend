package com.medtracker.services

import com.medtracker.models.User
import com.medtracker.repositories.AgendaRepository
import com.medtracker.repositories.UserRepository


class UserService {

    fun login(includedResources: List<String>?){
        val userRepository = UserRepository()
        return userRepository.login(includedResources)
    }



}