package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import models.Product
import repositories.ProductRepository

@Singleton
class ProductController @Inject()(
    val controllerComponents: ControllerComponents,
    productRepo: ProductRepository
) extends BaseController {

    def validateProduct(product: Product): Option[String] = {
        if (product.price <= 0) Some("Price must be greater than 0")
        else if (product.stock < 0) Some("Stock cannot be negative")
        else if (product.categoryId <= 0) Some("Category ID must be greater than 0")
        else if (product.name.trim.isEmpty) Some("Product name cannot be empty")
        else if (product.description.trim.isEmpty) Some("Product description cannot be empty")
        else None
    }

    def showAll = Action {
        Ok(Json.toJson(productRepo.all()))
    }

    def showById(id: Long) = Action {
        productRepo.findById(id) match {
            case Some(product) => Ok(Json.toJson(product))
            case None => NotFound(Json.obj("error" -> "Product not found"))
        }
    }

    def add: Action[JsValue] = Action(parse.json) { request =>
        request.body.validate[Product].fold(
            errors => BadRequest(Json.obj("error" -> "Invalid JSON", "details" -> JsError.toJson(errors))),
            product => validateProduct(product) match {
                case Some(error) => BadRequest(Json.obj("error" -> error))
                case None =>
                    val created = productRepo.add(product)
                    Created(Json.toJson(created))
            }
        )
    }

    def update(id: Long): Action[JsValue] = Action(parse.json) { request =>
        request.body.validate[Product].fold(
            errors => BadRequest(Json.obj("error" -> "Invalid JSON", "details" -> JsError.toJson(errors))),
            product => validateProduct(product) match {
                case Some(error) => BadRequest(Json.obj("error" -> error))
                case None =>
                    productRepo.update(id, product) match {
                        case Some(updated) => Ok(Json.toJson(updated))
                        case None => NotFound(Json.obj("error" -> "Product not found"))
                    }
            }
        )
    }   

    def delete(id: Long) = Action {
        if (productRepo.delete(id)) NoContent
        else NotFound(Json.obj("error" -> "Product not found"))
    }
}

