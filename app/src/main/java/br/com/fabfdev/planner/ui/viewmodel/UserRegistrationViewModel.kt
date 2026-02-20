package br.com.fabfdev.planner.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fabfdev.planner.data.datasource.UserRegistrationLocalDataSource
import br.com.fabfdev.planner.data.di.MainServiceLocator

class UserRegistrationViewModel: ViewModel() {

    private val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        MainServiceLocator.userRegistrationLocalDataSource
    }

    fun isUserRegistered(): Boolean {
        return userRegistrationLocalDataSource.isUserRegistered()
    }

    fun saveIsUserRegistered(isUserRegistered: Boolean) {
        userRegistrationLocalDataSource.saveIsUserRegistered(isUserRegistered)
    }

}