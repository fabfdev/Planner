package br.com.fabfdev.planner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fabfdev.planner.data.datasource.AuthenticationLocalDataSource
import br.com.fabfdev.planner.data.datasource.UserRegistrationLocalDataSource
import br.com.fabfdev.planner.data.di.MainServiceLocator
import br.com.fabfdev.planner.data.model.Profile
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private val mockToken = """
    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
    .eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0
    .KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
""".trimIndent()

class UserRegistrationViewModel : ViewModel() {

    private val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        MainServiceLocator.userRegistrationLocalDataSource
    }

    private val authenticationLocalDataSource: AuthenticationLocalDataSource by lazy {
        MainServiceLocator.authenticationLocalDataSource
    }

    private val _isProfileValid = MutableStateFlow(false)
    val isProfileValid: StateFlow<Boolean> = _isProfileValid.asStateFlow()

    private val _profile: MutableStateFlow<Profile> = MutableStateFlow(
        Profile()
    )
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    private val _isTokenValid: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isTokenValid: StateFlow<Boolean?> = _isTokenValid.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                userRegistrationLocalDataSource.profile.collect { profile ->
                    _profile.value = profile
                }
            }
            launch {
                while (true) {
                    val tokenExpirationDatetime = authenticationLocalDataSource
                        .expirationDatetime
                        .firstOrNull()
                    tokenExpirationDatetime?.let { tokenExpirationDatetime ->
                        val datetimeNow = System.currentTimeMillis()
                        _isTokenValid.value = tokenExpirationDatetime >= datetimeNow
                    }
                    delay(5_000)
                }
            }
        }
    }

    fun isUserRegistered(): Boolean {
        return userRegistrationLocalDataSource.isUserRegistered()
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

    fun saveProfile(onCompleted: () -> Unit) {
        viewModelScope.launch {
            async {
                userRegistrationLocalDataSource.saveProfile(profile = profile.value)
                userRegistrationLocalDataSource.saveIsUserRegistered(isUserRegistered = true)
                authenticationLocalDataSource.insertToken(mockToken)
                _isTokenValid.value = true
            }.await()
            onCompleted()
        }
    }

    fun obtainNewToken() {
        viewModelScope.launch {
            authenticationLocalDataSource.insertToken(mockToken)
            _isTokenValid.value = true
        }
    }

}