
package openapi.dsl {

  import io.swagger.v3.oas.models.PathItem.HttpMethod
  import io.swagger.v3.oas.models.tags.Tag
  import io.swagger.v3.oas.models.{OpenAPI, Operation, PathItem}

  object swagger {

    // ****** CATALOG ******
    class catalog(private val config: (OpenAPI) => Unit) {
      private[dsl] val swaggerElement = new OpenAPI
      config apply swaggerElement

      private def contains[T](obj: T, list: java.util.List[T], mapper: (T) => _): Boolean = {
        if (list == null) {
          false
        } else {
          import scala.collection.JavaConverters._
          val objValue = mapper.apply(obj)
          list.asScala.map(mapper).filter(_ == objValue).nonEmpty
        }
      }

      private def clone[T](bean: T) = {
        org.apache.commons.beanutils.BeanUtils.cloneBean(bean).asInstanceOf[T]
      }

      def +=(method: method) = {

        // Add tag
        {
          // Add copy of a tag iff not yet present
          // (tags are considered to be equal if their names are equal, see tag.id function)
          method.tags
            .filterNot((t: tag) => contains[Tag](t.swaggerElement, swaggerElement.getTags, tag.id))
            .foreach((t: tag) => swaggerElement.addTagsItem(clone(t.swaggerElement)))
        }

        // Add path item and operation
        {
          // Add copy of a path element iff not yet present
          // (path element is considered to be present if a non-null value exists under String key representing url)
          val swaggerPathItem = if (swaggerElement.getPaths == null) {
            val pathItem = clone(method.endpoint.swaggerElement)
            swaggerElement.path(method.endpoint.url, pathItem)
            pathItem
          } else {
            swaggerElement.getPaths.computeIfAbsent(method.endpoint.url, _ => clone(method.endpoint.swaggerElement))
          }
          // Add copy of an operation for the path item
          // (existing operation for the given HTTP method will be replaced, if there is any)
          swaggerPathItem.operation(method.httpMethod, clone(method.swaggerElement))
        }
      }

      def swagger: OpenAPI = swaggerElement

      override def toString: String = s"catalog[$swaggerElement]"
    }

    object catalog {
      def apply(config: (OpenAPI) => Unit): catalog = new catalog(config)
    }

    // ****** TAG ******
    class tag(private[dsl] val name: String) {
      private[dsl] val swaggerElement = new Tag
      swaggerElement name name

      def desc(desc: String): tag = {
        swaggerElement description desc
        this
      }

      // ... other definitions ...
      // TODO externalDocs
    }

    object tag {
      def apply(name: String): tag = new tag(name)

      def id(swaggerElement: Tag): String = swaggerElement.getName
    }

    // ****** ENDPOINT ******
    class endpoint(private[dsl] val url: String) {
      private[dsl] val swaggerElement = new PathItem

      def summary(summary: String): endpoint = {
        swaggerElement summary summary
        this
      }

      def desc(desc: String): endpoint = {
        swaggerElement description desc
        this
      }

      // ... other definitions ...
      // TODO servers, parameters
    }

    object endpoint {
      def apply(url: String): endpoint = new endpoint(url)
    }

    // ****** METHOD ******
    class method private(private[dsl] val swaggerElement: Operation = new Operation, private[dsl] val httpMethod: HttpMethod, private[dsl] val endpoint: endpoint, private[dsl] val tags: Set[tag] = Set.empty) {

      /**
        * A list of tags for API documentation control.
        * Tags can be used for logical grouping of operations by resources or any other qualifier.
        *
        * @param tags
        * @return
        */
      def tag(tags: tag*): method = {
        tags.foreach(swaggerElement addTagsItem _.name)
        new method(swaggerElement, httpMethod, endpoint, this.tags ++ tags)
      }

      /**
        * A short summary of what the operation does.
        *
        * @param summary
        * @return
        */
      def summary(summary: String): method = {
        swaggerElement summary summary
        this
      }

      /**
        * A verbose explanation of the operation behavior.
        * CommonMark syntax MAY be used for rich text representation.
        *
        * @param desc
        * @return
        */
      def desc(desc: String): method = {
        swaggerElement description desc
        this
      }

      /**
        * Unique string used to identify the operation. The id MUST be unique among all operations described in the API.
        * Tools and libraries MAY use the operationId to uniquely identify an operation, therefore, it is RECOMMENDED
        * to follow common programming naming conventions.
        *
        * @param opId
        * @return
        */
      def operationId(opId: String): method = {
        swaggerElement operationId opId
        this
      }

      /**
        * Declares this operation to be deprecated.
        * Consumers SHOULD refrain from usage of the declared operation. Default value is {@code false}.
        *
        * @return
        */
      def deprecated(): method = {
        swaggerElement deprecated true
        this
      }

      // ... other definitions ...
      // TODO externalDocs, servers, security

      def to(catalogs: Seq[catalog]) = catalogs.foreach(catalog => catalog += this)
    }

    object method {
      def apply(m: HttpMethod, e: endpoint): method = new method(httpMethod = m, endpoint = e)
    }

  }
}
