package tw.harry.springboot.spring06.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring06.entitiy.Member
import java.util.Optional

@Repository
interface MemberRepo : JpaRepository<Member, Long> {
    fun findByAccount(account: String): Optional<Member>
}