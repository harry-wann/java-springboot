package tw.harry.springboot.spring03.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring03.entity.Gift
import tw.harry.springboot.spring03.entity.Member

@Repository
interface MemberRepo : JpaRepository<Member, Long> {
    fun findByAccount(account: String): Member?
}