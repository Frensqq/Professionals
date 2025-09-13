package com.example.professionals.data.domainmodel

import java.io.File

data class UserUpdate(
    val oldPassword: String,
    val password: String,
    val passwordConfirm: String
)