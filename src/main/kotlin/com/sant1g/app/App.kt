package com.sant1g.app

import com.sant1g.app.modules.Component
import com.sant1g.app.modules.ModuleLoader
import com.sant1g.app.modules.inject
import com.sant1g.app.repositories.UnsplashRepository
import com.sant1g.app.server.Server
import kotlinx.coroutines.runBlocking

class App : Component {
  private val server: Server by inject()

  init {
    ModuleLoader.init()

    runBlocking {
      with(getKoin()) {
        get<UnsplashRepository>().init()
      }
    }
  }

  fun start() {
    server.start()
  }

  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val app = App()
      app.start()
    }
  }
}
