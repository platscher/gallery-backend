package com.sant1g.app.server

import com.sant1g.app.configuration.Config
import com.sant1g.app.configuration.ServerConfig
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

class Server(private val appConfig: Config) {

  fun start() {
    val env = getApplicationEngineEnvironment()
    embedServer(env)
  }

  private fun embedServer(env: ApplicationEngineEnvironment) {
    embeddedServer(Netty, env).start(wait = true)
  }

  private fun getApplicationEngineEnvironment(): ApplicationEngineEnvironment {
    return applicationEngineEnvironment {
      this.config = ServerConfig(appConfig)
      connector {
        this.host = appConfig.get("ktor.deployment.host")
        this.port = appConfig.get("ktor.deployment.port")
      }
    }
  }
}
