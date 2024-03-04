package com.maxxxwk.mornhousetesttask.di

import android.content.Context
import com.maxxxwk.mornhousetesttask.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        LocalDBModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
