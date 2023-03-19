package com.sant1g.app.metrics

import io.micrometer.core.instrument.Tag

object Metrics {

  private val registry = KtorMicrometer.getRegistry()

  fun incrementCustomMetric(metricName: String, tags: Iterable<Tag>) {
    increment(
      metricName,
      tags
    )
  }

  private fun increment(metricName: String, tags: Iterable<Tag>) {
    registry.counter(metricName, tags).increment()
  }
}
