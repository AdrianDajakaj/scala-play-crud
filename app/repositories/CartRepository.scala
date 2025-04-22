package repositories

import models._
import scala.collection.mutable.ListBuffer
import javax.inject.{Inject, Singleton}

@Singleton
class CartRepository @Inject()(productRepo: ProductRepository) {
    private val carts = ListBuffer.empty[Cart]
    private var currentId: Long = 0

    private def nextId(): Long = {
        currentId += 1
        currentId
    }

    def all(): Seq[Cart] = carts.toList

    def findById(id: Long): Option[Cart] = carts.find(_.id == id)

    def add(items: Seq[CartItem]): Either[String, Cart] = {
        val validatedItems = items.map { item =>
            productRepo.findById(item.productId) match {
                case Some(p) if item.quantity <= p.stock =>
                val price = p.discount match {
                    case Some(d) => p.price * (1 - d / 100)
                    case None => p.price
                }
                productRepo.updateStock(item.productId, p.stock - item.quantity)
                Right((item, price * item.quantity))
                case Some(_) => Left(s"Insufficient stock for product ${item.productId}")
                case None => Left(s"Product not found: ${item.productId}")  
            }
        }
        if (validatedItems.exists(_.isLeft)) 
            Left(validatedItems.collect { case Left(e) => e }.mkString("; "))
        else {
            val total = validatedItems.collect { case Right((_, subtotal)) => subtotal }.sum
            val newCart = Cart(nextId(), items, total)
            carts += newCart
            Right(newCart)
        }
    }


    def update(id: Long, updatedItems: Seq[CartItem]): Either[String, Cart] = {
        findById(id) match {
            case Some(_) =>
            val validatedItems = updatedItems.map { item =>
            productRepo.findById(item.productId) match {
                    case Some(p) if item.quantity <= p.stock =>
                    val price = p.discount match {
                        case Some(d) => p.price * (1 - d / 100)
                        case None => p.price
                    }
                    val previousQuantity = findById(id).get.items.find(_.productId == item.productId).get.quantity
                    productRepo.updateStock(item.productId, p.stock + previousQuantity - item.quantity)
                    Right((item, price * item.quantity))
                    case Some(_) => Left(s"Insufficient stock for product ${item.productId}")
                    case None => Left(s"Product not found: ${item.productId}")
                }
            }
            if (validatedItems.exists(_.isLeft)) 
                Left(validatedItems.collect { case Left(e) => e }.mkString("; "))
            else {
                val total = validatedItems.collect { case Right((_, subtotal)) => subtotal }.sum
                val updatedCart = Cart(id, updatedItems, total)
                carts.indexWhere(_.id == id) match {
                    case i if i >= 0 =>
                    carts.update(i, updatedCart)
                    Right(updatedCart)
                    case _ =>
                    Left("Cart not found during update")
                }
            }
            case None => Left("Cart not found")
        }
    }

    def delete(id: Long): Boolean = {
        val maybeCart = findById(id)
        maybeCart.foreach { cart =>
            cart.items.foreach { item =>
                productRepo.findById(item.productId).foreach { product =>
                    productRepo.updateStock(item.productId, product.stock + item.quantity)
                }
            }
            carts -= cart
        }
        maybeCart.isDefined
    }

}
