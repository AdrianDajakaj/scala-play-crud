package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models._
import repositories._

@Singleton
class CartController @Inject()(
    val controllerComponents: ControllerComponents,
    cartRepo: CartRepository
) extends BaseController {

    def showAll = Action {
        Ok(Json.toJson(cartRepo.all()))
    }

    def showById(id: Long) = Action {
        cartRepo.findById(id) match {
            case Some(cart) => Ok(Json.toJson(cart))
            case None => NotFound(Json.obj("error" -> "Cart not found"))
        }
    } 

    def add: Action[JsValue] = Action(parse.json) { request =>
        request.body.validate[Seq[CartItem]].fold(
            errors => BadRequest(Json.obj("error" -> "Invalid JSON", "details" -> JsError.toJson(errors))),
            items => cartRepo.add(items) match {
                case Right(cart) => Created(Json.toJson(cart))
                case Left(error) => BadRequest(Json.obj("error" -> error))
            }
        )
    }

    def update(id: Long): Action[JsValue] = Action(parse.json) { request =>
        request.body.validate[Seq[CartItem]].fold(
            errors => BadRequest(Json.obj("error" -> "Invalid JSON", "details" -> JsError.toJson(errors))),
            items => cartRepo.update(id, items) match {
                case Right(cart) => Ok(Json.toJson(cart))
                case Left(error) => BadRequest(Json.obj("error" -> error))
            }
        )
    }

    def delete(id: Long) = Action {
        if (cartRepo.delete(id)) NoContent
        else NotFound(Json.obj("error" -> "Cart not found"))
    }
}
