package Koin

import domain.BibliaDBRepository
import domain.BibliaViewModel
import domain.RepositoryImpl
import org.diose.bibliacomposekmp.BibliaDatabase
import org.koin.core.context.startKoin
import org.koin.core.module.KoinApplicationDslMarker
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.qualifier
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val sharedModule = module {
    single<BibliaDBRepository> {
        RepositoryImpl()
    }.withOptions {
        createdAtStart()
        named("Repository")
    }

    single {
        BibliaViewModel(repository = get(qualifier = qualifier("Repository")))
    }
}


@KoinApplicationDslMarker
fun startKoinCommon(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(sharedModule)
    }
}