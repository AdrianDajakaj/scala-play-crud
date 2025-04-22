error id: `<none>`.
file://<WORKSPACE>/app/models/Product.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 47
uri: file://<WORKSPACE>/app/models/Product.scala
text:
```scala
package models
import java.time.Instant

case c@@lass Product(id: Long, name: String, categoryId: Long, description: String, stock: Int, price: BigDecimal, currency: String, discount: Option[BigDecimal] = None, isActive: Boolean = true, createdAt: Instant = Instant.now(), updatedAt: Instant = Instant.now())

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.