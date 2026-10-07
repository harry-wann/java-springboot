package tw.harry.springboot.spring04.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Member(
    @Id
    var id: Long? = null,
    var account: String = "",
    var passwd: String = "",
    var name: String = ""
)