package tw.harry.springboot.spring01.utils

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import tw.harry.springboot.spring01.dto.Gift
import java.sql.ResultSet

@Component
class GiftRowMapper: RowMapper<Gift> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Gift {
        return Gift(
            id = rs.getInt("id"),
            name = rs.getString("name"),
            addr = rs.getString("addr"),
            tel = rs.getString("tel"),
        )
    }
}