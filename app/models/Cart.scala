package models

import play.api.libs.json._

case class Cart(id: Long, items: Seq[CartItem], total: BigDecimal)

object Cart {
    implicit val format: OFormat[Cart] = Json.format[Cart]
}
