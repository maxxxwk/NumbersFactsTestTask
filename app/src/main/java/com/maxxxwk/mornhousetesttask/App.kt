package com.maxxxwk.mornhousetesttask

import android.app.Application
import com.maxxxwk.mornhousetesttask.di.AppComponent
import com.maxxxwk.mornhousetesttask.di.DaggerAppComponent

class App : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}
