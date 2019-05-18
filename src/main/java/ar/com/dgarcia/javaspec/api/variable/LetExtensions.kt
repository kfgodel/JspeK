package ar.com.dgarcia.javaspec.api.variable

fun <T> Let<T>.set(definition: () -> T): Let<T> {
  context().let(variableName(), definition)
  return Let.create(variableName()) { context() }
}