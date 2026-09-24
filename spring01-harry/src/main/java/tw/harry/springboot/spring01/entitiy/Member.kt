package tw.harry.springboot.spring01.entitiy

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var account: String? = null,

    @Column(name = "passwd")
    var password: String? = null,
    var name: String? = null,

    @Lob
    var icon: ByteArray? = null,
)