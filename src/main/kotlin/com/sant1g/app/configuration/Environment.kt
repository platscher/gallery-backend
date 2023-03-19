package com.sant1g.app.configuration

object Environment {

  private const val ENV_VARIABLE = "ENV"
  private const val DEVELOPMENT = "dev"
  private const val PRODUCTION = "live"
  private const val EMPTY_VALUE = ""
  private val CURRENT_ENV: String

  init {
    val env = System.getenv(ENV_VARIABLE)
    CURRENT_ENV = if (env.isNullOrEmpty()) DEVELOPMENT else env
  }

  fun isDevelopment(): Boolean {
    return DEVELOPMENT.contains(CURRENT_ENV)
  }

  fun isProduction(): Boolean {
    return PRODUCTION.contains(CURRENT_ENV)
  }

  fun getVariableOrDefault(name: String, defaultValue: String = EMPTY_VALUE): String {
    return System.getenv(name) ?: defaultValue
  }

  fun getEnvironment() = CURRENT_ENV
}
