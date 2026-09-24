package tw.harry.springboot.spring01.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Gift(
    var id: Int? = null,
    @JsonProperty("Name")
    var name: String? = null,
    @JsonProperty("SalePlace")
    var addr: String? = null,
    @JsonProperty("ContactTel")
    var tel: String? = null,
    var error: Int? = null,
)