package openapi.examples

import io.swagger.v3.oas.models.info.Info
import openapi.dsl.swagger

object SharedConfig {

  val CAT1 = swagger.catalog {
    _.setInfo((new Info).title("Catalog1").description("Desc1"))
  }
  val CAT2 = swagger.catalog {
    _.setInfo((new Info).title("Catalog2").version("v2.0"))
  }
  val CAT3 = swagger.catalog {
    _.setInfo((new Info).title("Catalog3").version("v3.0"))
  }
  val CAT_SET = CAT1 :: CAT3 :: Nil

  val TAG1 = swagger.tag("tag1").desc("tag1 desc")
  val TAG2 = swagger.tag("tag2").desc("tag2 desc")
  val TAG3 = swagger.tag("tag3").desc("tag3 desc")

  val TAG_SET = TAG1 :: TAG2 :: Nil

  val EP_A = swagger.endpoint("/a")
    .desc("/a desc")

}
