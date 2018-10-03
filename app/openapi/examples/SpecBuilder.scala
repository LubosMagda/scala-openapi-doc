package openapi.examples

import com.fasterxml.jackson.databind.{ObjectWriter, SerializationFeature}
import io.swagger.v3.core.util.Json
import io.swagger.v3.oas.models.info.{Contact, Info, License}
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.tags.Tag
import io.swagger.v3.oas.models.{ExternalDocumentation, OpenAPI, Operation, PathItem}
import play.api.mvc.Result
import play.api.mvc.Results.Ok

// https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md
object Spec {

  object Builder {

    // It is RECOMMENDED that the root OpenAPI document be named: openapi.json or openapi.yaml.
    // An OpenAPI document MAY be made up of a single document or be divided into multiple, connected parts.
    // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#documentStructure
    // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#dataTypes
    def oapi = {

      // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#licenseObject
      val license = new License
      license name "linense name"
      license url "http://licence.url"

      // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#contactObject
      val contact = new Contact
      contact name "contact name"
      contact url "http://contact.url"
      contact email "contact@email.com.au"

      // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#infoObject
      val info = {
        val info = new Info
        info title "A Title"
        // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#richText
        info description "A Descr / All `description` field support `https://spec.commonmark.org/0.27/`"
        info termsOfService "http://terms.of.service"
        info version "2.3.5"
        info license license
        info contact contact
        info
      }

      // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#externalDocumentationObject
      def extDoc(desc: String, url: String) = {
        val extDoc = new ExternalDocumentation
        extDoc description "`extDoc1` description"
        extDoc url "http://external.doc1"
        extDoc
      }

      // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#tagObject
      val tag1 = new Tag
      tag1 name "tag1Name"
      tag1 description "`tag1` description"
      tag1 externalDocs extDoc("`tag1.extDoc` description", "http://external.doc.for.tag1")

      val tag2 = new Tag
      tag2 name "tag2Name"
      tag2 description "`tag2` description"
      tag2 externalDocs extDoc("`tag2.extDoc` description", "http://external.doc.for.tag2")

      // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#pathsObject
      // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#pathItemObject
      def path = {
        val pathItem = new PathItem
        //pathItem $ref "$ref" // TODO what should be here?
        pathItem summary "`pathItem` summary"
        pathItem description "`pathItem` description"
        pathItem get operation("GET summary", "`GET method` desc", tag1)
        pathItem put operation("PUT summary", "`PUT method` desc", tag2)
        pathItem
      }

      // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#operationObject
      def operation(summary: String, description: String, tag: Tag) = {
        val op = new Operation
        op summary summary
        op description description
        //op addTagsItem tag.getName
        op
      }

      // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#serverObject
      // TODO variables
      val server1 = new Server
      server1 url "http://server1.url"
      server1 description "`Server 1` description"

      // https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#oasObject
      def oapi = {
        val oapi = new OpenAPI
        oapi info info
        oapi addServersItem server1
        oapi path("/path/1", path)
        oapi addTagsItem tag1
        oapi addTagsItem tag2
        oapi externalDocs extDoc("`extDoc1` description", "http://external.doc1")
        oapi
      }

      oapi
    }
  }

  object Serializer {

    private val jsonWriter: ObjectWriter = Json.mapper
      .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
      .writerWithDefaultPrettyPrinter

    def asString(oas: OpenAPI): String = jsonWriter.writeValueAsString(oas)

    def asResult(oas: OpenAPI): Result = {
      // JSON or YAML schema / https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#format
      Ok(asString(oas))
        // openapi.v3 content type / https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#mediaTypes
        .as("application/vnd.github.v3+json")
    }
  }

}
