package br.com.fabfdev.planner.data.di

import android.app.Application
import br.com.fabfdev.planner.data.datasource.AuthenticationLocalDataSource
import br.com.fabfdev.planner.data.datasource.AuthenticationLocalDataSourceImpl
import br.com.fabfdev.planner.data.datasource.UserRegistrationLocalDataSource
import br.com.fabfdev.planner.data.datasource.UserRegistrationLocalDataSourceImpl

object MainServiceLocator {

    private lateinit var application: Application

    val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        UserRegistrationLocalDataSourceImpl(application.applicationContext)
    }

    val authenticationLocalDataSource: AuthenticationLocalDataSource by lazy {
        AuthenticationLocalDataSourceImpl(application.applicationContext)
    }

    fun initialize(application: Application) {
        this.application = application
    }

}