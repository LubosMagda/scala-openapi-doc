package test.openapi.dsl

import io.swagger.v3.oas.models.PathItem.HttpMethod._
import openapi.dsl.swagger
import openapi.examples.SharedConfig._
import openapi.examples.Spec.Serializer
import org.scalatest.FlatSpec
import play.api.Logger

class OpenAPISpec extends FlatSpec {
  private[this] val log = Logger(this.getClass)

  "Method" should "add a Swagger Operation" in {

    swagger.method(GET, EP_A)
      .tag(TAG1, TAG3)
      .desc("Desc1")
      .to(CAT_SET)

    swagger.method(POST, EP_A)
      .desc("jklo")
      .summary("Summary2")
      .to(CAT1 :: Nil)

    swagger.method(DELETE, EP_A)
      .tag(TAG2)
      .desc("jklo")
      .operationId("opId3")
      .to(CAT1 :: Nil)

    log.debug(s"CAT1: ${Serializer.asString(CAT1.swagger)}")
    log.debug(s"CAT2: ${Serializer.asString(CAT2.swagger)}")
    log.debug(s"CAT3: ${Serializer.asString(CAT3.swagger)}")
  }
}
