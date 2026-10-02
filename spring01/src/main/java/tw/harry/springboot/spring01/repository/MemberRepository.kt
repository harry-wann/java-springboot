package tw.harry.springboot.spring01.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring01.entity.Member
import java.util.Optional

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByAccount(account: String): Boolean
    fun findByAccount(account: String): Optional<Member>
}