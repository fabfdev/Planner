package br.com.fabfdev.planner.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val USER_REGISTRATION_FILE_NAME = "user_registration"
private const val IS_USER_REGISTERED_KEY = "is_user_registered"

class UserRegistrationLocalDataSourceImpl(
    applicationContext: Context,
): UserRegistrationLocalDataSource {

    val userRegistrationSharedPreferences: SharedPreferences =
        applicationContext.getSharedPreferences(
            USER_REGISTRATION_FILE_NAME,
            Context.MODE_PRIVATE
        )

    override fun isUserRegistered(): Boolean {
        return userRegistrationSharedPreferences.getBoolean(
            IS_USER_REGISTERED_KEY,
            false
        )
    }

    override fun saveIsUserRegistered(isUserRegistered: Boolean) {
        userRegistrationSharedPreferences.edit {
            putBoolean(IS_USER_REGISTERED_KEY, isUserRegistered)
        }
    }
}