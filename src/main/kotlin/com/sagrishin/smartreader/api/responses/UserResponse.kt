package com.sagrishin.smartreader.api.responses

import com.sagrishin.smartreader.models.repositories.models.User

data class UserResponse(val user: User, val status: Int)