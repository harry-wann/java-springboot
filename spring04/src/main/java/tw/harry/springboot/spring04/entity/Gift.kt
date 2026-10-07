package tw.harry.springboot.spring04.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "gifts")
class Gift(
    @Id
    var id: Long = 0,
    var name: String = "",
    var addr: String = "",
    var tel: String = "",
) {
}