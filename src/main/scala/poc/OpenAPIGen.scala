package poc

import com.fasterxml.jackson.databind.SerializationFeature
import io.swagger.v3.core.util.{Json, Yaml}
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info

object OpenAPIGen extends App {

  val info = new Info
  info title "title"
  info description "descr"

  val oapi = new OpenAPI
  oapi info info

  println("===YAML===")
  println(Yaml.mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
    .writerWithDefaultPrettyPrinter.writeValueAsString(oapi))
  println("===JSON===")
  println(Json.mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
    .writerWithDefaultPrettyPrinter.writeValueAsString(oapi))
  println("===OBJECT===")
  println(oapi)
}
