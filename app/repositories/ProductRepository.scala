package repositories

import models.Product
import scala.collection.mutable.ListBuffer
import java.time.Instant

class ProductRepository {
    private val products = ListBuffer.empty[Product]
    private var currentId: Long = 0

    private def nextId(): Long = {
        currentId += 1
        currentId
    }

    def all(): Seq[Product] = products.toList

    def findById(id: Long): Option[Product] = products.find(_.id == id)

    def add(product: Product): Product = {
        val newProduct = product.copy(
            id = nextId(),
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        products += newProduct
        newProduct
    }

    def update(id: Long, updated: Product): Option[Product] = {
        findById(id).map { existing =>
        val newProduct = updated.copy(
            id = existing.id,
            createdAt = existing.createdAt,
            updatedAt = Instant.now()
        )
        products -= existing
        products += newProduct
        newProduct
        }
    }

    def delete(id: Long): Boolean = {
        val maybeProduct = findById(id)
        maybeProduct.foreach(products -= _)
        maybeProduct.isDefined
    }
}
