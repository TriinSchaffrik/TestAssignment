package ee.topgravity.android.testassignment

import android.app.Application
import ee.topgravity.android.testassignment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ProductApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ProductApplication)
            modules(appModule)
        }
    }
}