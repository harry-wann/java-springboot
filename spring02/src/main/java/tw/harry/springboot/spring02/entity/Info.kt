package tw.harry.springboot.spring02.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import lombok.Data
import org.springframework.data.jpa.repository.EntityGraph

@Entity
@Table(name = "memberinfo")
class Info(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    var tel: String? = null,

    @Column(name = "gender")
    var isMale: Boolean? = null,

    // -------------------
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    var member: Member? = null,
)
