package tw.harry.springboot.spring04.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring04.entity.Member
import java.util.Optional

@Repository
interface MemberRepo: JpaRepository<Member, Long> {
    fun findByAccount(account: String): Optional<Member>
}