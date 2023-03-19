package com.sant1g.app.metrics

import com.sant1g.app.configuration.Environment
import io.micrometer.core.instrument.Clock
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.binder.MeterBinder
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.statsd.StatsdConfig
import io.micrometer.statsd.StatsdMeterRegistry

object KtorMicrometer {

  private const val DD_AGENT_HOST_ENV_VARIABLE: String = "DD_AGENT_HOST"
  private const val PORT: Int = 8125
  private const val ENABLED: Boolean = true
  private const val STEP: String = "PT30S"
  private const val SERVICE_METRICS_PREFIX = "peya.service"
  private val HOST: String = Environment.getVariableOrDefault(DD_AGENT_HOST_ENV_VARIABLE)

  private val REGISTRY by lazy {
    getStatsdMeterRegistry()
  }

  fun getRegistry(): MeterRegistry {
    return REGISTRY
  }

  fun meterBinders(): List<MeterBinder> {
    return listOf(
      ClassLoaderMetrics(),
      JvmMemoryMetrics(),
      JvmGcMetrics(),
      ProcessorMetrics(),
      JvmThreadMetrics(),
      FileDescriptorMetrics()
    )
  }

  private fun getStatsdMeterRegistry(): StatsdMeterRegistry {
    val statsdConfig = getMeterConfig()
    val statsdMeterRegistry = StatsdMeterRegistry(statsdConfig, Clock.SYSTEM)
    statsdMeterRegistry.Config()
      .namingConvention { name, _, _ -> "$SERVICE_METRICS_PREFIX.$name" }
      .commonTags(
        listOf(
          Tag.of("env", Environment.getEnvironment()),
          Tag.of("service", "@project_name@"),
          Tag.of("app", "@project_name@")
        )
      )
    return statsdMeterRegistry
  }

  private fun getMeterConfig(): StatsdConfig {
    return StatsdConfig { path ->
      when (path) {
        "statsd.host" -> HOST
        "statsd.port" -> PORT.toString()
        "statsd.enabled" -> ENABLED.toString()
        "statsd.step" -> STEP
        else -> null
      }
    }
  }
}
