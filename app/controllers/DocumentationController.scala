package controllers

import com.fasterxml.jackson.databind.{ObjectWriter, SerializationFeature}
import io.swagger.v3.core.util.Json
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import javax.inject.Inject
import play.api.mvc.{BaseController, ControllerComponents}

class DocumentationController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def json = Action {
    // Better approach would probably be JsValue
    Ok(DocumentationController.jsonWriter.writeValueAsString(oapi))
      .as("application/json")
  }

  def oapi: OpenAPI = {

    val info = new Info
    info title "title"
    info description "descr"

    val oapi = new OpenAPI
    oapi info info

    oapi
  }
}

object DocumentationController {
  val jsonWriter: ObjectWriter = Json.mapper
    .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
    .writerWithDefaultPrettyPrinter
}