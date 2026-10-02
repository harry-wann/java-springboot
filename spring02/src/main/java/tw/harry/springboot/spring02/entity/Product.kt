package tw.harry.springboot.spring02.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
data class Product(
    @Id
    var productId: Int? = null,

    var productName: String? = null,
)
