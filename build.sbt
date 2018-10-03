name := "scala-openapi-doc"

version := "0.1"

scalaVersion := "2.12.6"

// Enable PlayScala plugin for the project
lazy val root = (project in file(".")).enablePlugins(PlayScala)
libraryDependencies += guice

val swaggerVersion = "2.0.5"

// OpenAPI v3 model
libraryDependencies += "io.swagger.core.v3" % "swagger-models" % swaggerVersion

// OpenAPI v3 serializers/deserializers
libraryDependencies += "io.swagger.core.v3" % "swagger-jaxrs2" % swaggerVersion

// POJO cloning
libraryDependencies += "commons-beanutils" % "commons-beanutils" % "1.9.3"

// ScalaTest + Play
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
