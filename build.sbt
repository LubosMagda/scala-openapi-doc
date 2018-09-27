name := "scala-openapi-doc"

version := "0.1"

scalaVersion := "2.12.6"

val swaggerVersion = "2.0.5"

// OpenAPI v3 model
libraryDependencies += "io.swagger.core.v3" % "swagger-models" % swaggerVersion

// OpenAPI v3 serializers/deserializers
libraryDependencies += "io.swagger.core.v3" % "swagger-jaxrs2" % swaggerVersion