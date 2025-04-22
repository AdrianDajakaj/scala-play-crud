package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.Category
import repositories.CategoryRepository

@Singleton
class CategoryController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

    private val repo = new CategoryRepository()

    def showAll = Action {
        Ok(Json.toJson(repo.all()))
    }

    def showById(id: Long) = Action {
        repo.findById(id) match {
            case Some(cat) => Ok(Json.toJson(cat))
            case None => NotFound(Json.obj("error" -> "Category not found"))
        }
    }

    def add: Action[JsValue] = Action(parse.json) { request =>
        request.body.validate[Category].fold(
            errors => BadRequest(Json.obj("error" -> "Invalid JSON", "details" -> JsError.toJson(errors))),
            cat => {
                if (cat.name.trim.isEmpty) BadRequest(Json.obj("error" -> "Category name cannot be empty"))
                else Created(Json.toJson(repo.add(cat)))
            }
        )
    }

    def update(id: Long): Action[JsValue] = Action(parse.json) { request =>
        request.body.validate[Category].fold(
            errors => BadRequest(Json.obj("error" -> "Invalid JSON", "details" -> JsError.toJson(errors))),
            cat => repo.update(id, cat) match {
                case Some(updated) => Ok(Json.toJson(updated))
                case None => NotFound(Json.obj("error" -> "Category not found"))
            }
        )
    }

    def delete(id: Long) = Action {
        if (repo.delete(id)) NoContent
        else NotFound(Json.obj("error" -> "Category not found"))
    }
}
