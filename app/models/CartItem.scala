package models
import play.api.libs.json._

case class CartItem(productId: Long, quantity: Int)

object CartItem {
    implicit val format: OFormat[CartItem] = Json.format[CartItem]
}
