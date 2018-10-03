package controllers

import javax.inject.Inject
import play.api.mvc.{BaseController, ControllerComponents}

class DocumentationController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def json(id: String) = Action {
    import openapi.examples.{Dsl, Spec}

    Spec.Serializer.asResult(Dsl.Builder.oapi.get(id).getOrElse(Spec.Builder.oapi))
  }
}