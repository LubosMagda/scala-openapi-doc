package openapi.examples

import com.sun.prism.Texture.Usage
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.PathItem.HttpMethod._
import openapi.dsl.swagger

object Dsl {

  object Builder {

    import openapi.examples.SharedConfig._

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

    val usage: Usage = null
    val oapi: Map[String, OpenAPI] = Map(
      "CAT1" -> CAT1.swagger,
      "CAT2" -> CAT2.swagger,
      "CAT3" -> CAT3.swagger)
  }

}
