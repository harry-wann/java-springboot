package tw.harry.springboot.spring01.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne

data class Info(
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId("id")
    @JsonBackReference
    val id: Long = 0,
    var name: String = "",
    var tel: String = "",
    @Column(name = "gender")
    var isMale: Boolean = true,
)
