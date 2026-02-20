package br.com.fabfdev.planner.data.datasource

interface UserRegistrationLocalDataSource {
    fun isUserRegistered(): Boolean
    fun saveIsUserRegistered(isUserRegistered: Boolean)
}