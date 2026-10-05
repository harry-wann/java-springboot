package tw.harry.springboot.spring03.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "gifts")
class Gift(
    @Id
    var id: Long? = null,
    var name: String? = null,
    var addr: String? = null,
    var tel: String? = null,
)