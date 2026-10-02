package tw.harry.springboot.spring02.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var account: String? = null,
    @Column(name = "password")
    var passwd: String? = null,
) {
    // ---------------------
    @OneToOne(
        mappedBy = "member",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER
    )
    final var info: Info? = null
        private set

    fun setInfo(info: Info?) {
        this.info = info
        if (info != null) {
            info.member = this
        }
    }
}
