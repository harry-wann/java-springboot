package tw.harry.springboot.spring01.service

import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import tw.harry.springboot.spring01.entitiy.Member
import tw.harry.springboot.spring01.repository.MemberRepository

@Service
class MemberService(
    @Autowired
    private val memberRepository: MemberRepository
) {

    fun checkAccountExists(account: String): Boolean {
        return memberRepository.existsByAccount(account);
    }

    fun registerMember(member: Member): Boolean {
        try {
            member.password = BCrypt.hashpw(member.password, BCrypt.gensalt())
            memberRepository.saveAndFlush(member)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}