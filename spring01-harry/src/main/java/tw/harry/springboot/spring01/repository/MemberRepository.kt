package tw.harry.springboot.spring01.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring01.entitiy.Member

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByAccount(account: String): Boolean
    fun findByAccount(account: String): List<Member>
}