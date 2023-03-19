package com.sant1g.app.modules

import com.sant1g.app.clients.unsplash.UnsplashClient
import com.sant1g.app.configuration.Config
import com.sant1g.app.repositories.UnsplashRepository
import com.sant1g.app.server.Server
import com.sant1g.app.services.PhotoService
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

object ModuleLoader {

  fun init() {
    startKoin {
      modules(loadModules())
    }
  }

  private fun loadModules(): List<Module> {
    return listOf(
      coreModule(),
      serverModule(),
      clientModule(),
      repositoryModule(),
      serviceModule()
    )
  }

  private fun coreModule(): Module {
    return module {
      single { Config() }
    }
  }

  private fun serverModule(): Module {
    return module {
      single { Server(get()) }
    }
  }

  private fun clientModule(): Module {
    return module {
      single { UnsplashClient(get()) }
    }
  }

  private fun repositoryModule(): Module {
    return module {
      single { UnsplashRepository(get()) }
    }
  }

  private fun serviceModule(): Module {
    return module {
      single { PhotoService(get()) }
    }
  }
}
