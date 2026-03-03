package br.com.fabfdev.planner.core.di

import android.app.Application
import androidx.room.Room
import br.com.fabfdev.planner.data.database.PLANNER_ACTIVITY_DATABASE_NAME
import br.com.fabfdev.planner.data.database.PlannerActivityDao
import br.com.fabfdev.planner.data.database.PlannerActivityDatabase
import br.com.fabfdev.planner.data.datasource.AuthenticationLocalDataSource
import br.com.fabfdev.planner.data.datasource.AuthenticationLocalDataSourceImpl
import br.com.fabfdev.planner.data.datasource.PlannerActivityLocalDataSource
import br.com.fabfdev.planner.data.datasource.PlannerActivityLocalDataSourceImpl
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

    val plannerActivityDao: PlannerActivityDao by lazy {
        val database = Room.databaseBuilder(
            application.applicationContext,
            PlannerActivityDatabase::class.java,
            PLANNER_ACTIVITY_DATABASE_NAME
        ).build()

        database.plannerActivityDao()
    }

    val plannerActivityLocalDataSource: PlannerActivityLocalDataSource by lazy {
        PlannerActivityLocalDataSourceImpl(plannerActivityDao = plannerActivityDao)
    }

    fun initialize(application: Application) {
        this.application = application
    }

}