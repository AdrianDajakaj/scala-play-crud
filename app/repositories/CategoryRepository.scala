package repositories

import models.Category
import scala.collection.mutable.ListBuffer

class CategoryRepository {
    private val categories = ListBuffer.empty[Category]
    private var currentId: Long = 0

    private def nextId(): Long = {
        currentId += 1
        currentId
    }

    def all(): Seq[Category] = categories.toList

    def findById(id: Long): Option[Category] = categories.find(_.id == id)

    def add(category: Category): Category = {
        val newCategory = category.copy(id = nextId())
        categories += newCategory
        newCategory
    }

    def update(id: Long, updated: Category): Option[Category] = {
        findById(id).map { existing =>
            val newCategory = updated.copy(id = existing.id)
            categories -= existing
            categories += newCategory
            newCategory
        }
    }

    def delete(id: Long): Boolean = {
        val maybeCategory = findById(id)
        maybeCategory.foreach(categories -= _)
        maybeCategory.isDefined
    }
}
