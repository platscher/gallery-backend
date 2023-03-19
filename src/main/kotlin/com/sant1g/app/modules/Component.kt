package com.sant1g.app.modules

import org.koin.core.KoinComponent
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

interface Component : KoinComponent

inline fun <reified T> Component.inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) { getKoin().get<T>(qualifier, parameters) }
