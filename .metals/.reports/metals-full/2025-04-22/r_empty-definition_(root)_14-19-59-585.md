error id: java/time/Instant#
file://<WORKSPACE>/app/models/Product.scala
empty definition using pc, found symbol in pc: java/time/Instant#
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -java/time/Instant#
	 -play/api/libs/json/Instant#
	 -Instant#
	 -scala/Predef.Instant#
offset: 484
uri: file://<WORKSPACE>/app/models/Product.scala
text:
```scala
package models
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.ZoneId
import play.api.libs.json._

case class Product(id: Long, name: String, categoryId: Long, description: String, stock: Int, price: BigDecimal, currency: String, discount: Option[BigDecimal] = None, createdAt: Instant = Instant.now(), updatedAt: Instant = Instant.now())

object Product {
  implicit val instantFormat: Format[Instant] = new Format[Instant] {
    def writes(i: Ins@@tant): JsValue = {
      val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Europe/Warsaw"))
      JsString(formatter.format(i))
    }

    def reads(json: JsValue): JsResult[Instant] = json match {
      case JsString(s) =>
        try {
          JsSuccess(Instant.from(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Europe/Warsaw")).parse(s)))
        } catch {
          case _: Exception => JsError("Invalid Instant format")
        }
      case _ => JsError("Invalid Instant format")
    }
  }

  // Format JSON dla Product
  implicit val format: OFormat[Product] = Json.format[Product]
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/time/Instant#