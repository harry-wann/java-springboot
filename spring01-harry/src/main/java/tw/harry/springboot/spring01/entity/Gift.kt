package tw.harry.springboot.spring01.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "gifts")
data class Gift(
    @Id
    val id: Long = 0,
    val name: String = "",
    val addr: String = "",
    val tel: String = "",
)
