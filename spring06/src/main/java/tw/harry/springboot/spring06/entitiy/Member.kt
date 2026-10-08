package tw.harry.springboot.spring06.entitiy

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
class Member(
    @Id
    var id: Long = 0,
    var account: String = "",
    var passwd: String = "",
    var name: String = "",
    var role: String = "",
)