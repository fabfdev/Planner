package br.com.fabfdev.planner

import android.app.Application
import br.com.fabfdev.planner.data.di.MainServiceLocator

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        MainServiceLocator.initialize(this)
    }

}