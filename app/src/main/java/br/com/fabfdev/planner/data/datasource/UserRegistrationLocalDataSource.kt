package br.com.fabfdev.planner.data.datasource

import br.com.fabfdev.planner.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface UserRegistrationLocalDataSource {
    fun isUserRegistered(): Boolean
    fun saveIsUserRegistered(isUserRegistered: Boolean)
    val profile: Flow<Profile>
    suspend fun saveProfile(profile: Profile)
}