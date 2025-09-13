package com.example.professionals.data.model

data class UserResponse (
    val id:String,
    val email: String,
    val password: String,
    val passwordConfirm: String,
    val verified: Boolean?,
    val name:String,
    val avatar:String?,
    val emailVisibility: Boolean?
)