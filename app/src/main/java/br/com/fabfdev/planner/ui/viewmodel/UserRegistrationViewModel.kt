package br.com.fabfdev.planner.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fabfdev.planner.data.datasource.UserRegistrationLocalDataSource
import br.com.fabfdev.planner.data.di.MainServiceLocator
import br.com.fabfdev.planner.data.model.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserRegistrationViewModel: ViewModel() {

    private val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        MainServiceLocator.userRegistrationLocalDataSource
    }

    private val _isProfileValid = MutableStateFlow(false)
    val isProfileValid: StateFlow<Boolean> = _isProfileValid.asStateFlow()

    private val _profile: MutableStateFlow<Profile> = MutableStateFlow(
        Profile()
    )
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    fun isUserRegistered(): Boolean {
        return userRegistrationLocalDataSource.isUserRegistered()
    }

    fun saveIsUserRegistered(isUserRegistered: Boolean) {
        userRegistrationLocalDataSource.saveIsUserRegistered(isUserRegistered)
    }

    fun updateProfile(
        name: String? = null,
        email: String? = null,
        phone: String? = null,
        image: String? = null,
    ) {
        if (name == null && email == null && phone == null && image == null) {
            return
        }

        _profile.update { current ->
            val updatedProfile = current.copy(
                name = name ?: current.name,
                email = email ?: current.email,
                phone = phone ?: current.phone,
                image = image ?: current.image,
            )
            _isProfileValid.update { updatedProfile.isValid() }
            updatedProfile
        }
    }

}