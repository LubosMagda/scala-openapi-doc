package controllers

import org.scalatest.Ignore
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
@Ignore
class DocumentationControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "DocumentationController GET" should {

    "render the json page from a new instance of controller" in {
      val controller = new DocumentationController(stubControllerComponents())
      val home = controller.json(null).apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }

    "render the json page from the application" in {
      val controller = inject[DocumentationController]
      val documentation = controller.json(null).apply(FakeRequest(GET, "/"))

      status(documentation) mustBe OK
      contentType(documentation) mustBe Some("text/html")
      contentAsString(documentation) must include ("Welcome to Play")
    }

    "render the json page from the router" in {
      val request = FakeRequest(GET, "/")
      val documentation = route(app, request).get

      status(documentation) mustBe OK
      contentType(documentation) mustBe Some("text/html")
      contentAsString(documentation) must include ("Welcome to Play")
    }
  }
}
