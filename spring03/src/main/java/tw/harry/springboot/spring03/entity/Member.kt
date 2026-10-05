package tw.harry.springboot.spring03.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var account: String? = null,
    @Column("passwd")
    var password: String? = null,
    var name: String? = null,
    var icon: ByteArray? = null,
)