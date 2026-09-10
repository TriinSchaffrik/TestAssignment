package ee.topgravity.android.testassignment.di

import ee.topgravity.android.testassignment.data.repository.ProductRepository
import ee.topgravity.android.testassignment.data.repository.ProductRepositoryImpl
import ee.topgravity.android.testassignment.data.service.ProductApi
import ee.topgravity.android.testassignment.ui.products.ProductListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single { get<Retrofit>().create(ProductApi::class.java) }

    single {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    viewModel {
        ProductListViewModel(get())
    }
}