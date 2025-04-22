package models
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.ZoneId
import play.api.libs.json._

case class Product(id: Long, name: String, categoryId: Long, description: String, stock: Int, price: BigDecimal, currency: String, discount: Option[BigDecimal] = None, createdAt: Instant = Instant.now(), updatedAt: Instant = Instant.now())

object Product {
    implicit val instantFormat: Format[Instant] = new Format[Instant] {
        def writes(i: Instant): JsValue = {
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

    implicit val format: OFormat[Product] = Json.format[Product]
}
