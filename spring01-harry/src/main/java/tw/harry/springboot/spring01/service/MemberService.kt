package tw.harry.springboot.spring01.service

import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import tw.harry.springboot.spring01.entity.Member
import tw.harry.springboot.spring01.repository.MemberRepository
import kotlin.jvm.optionals.getOrNull

@Service
class MemberService(
    @Autowired
    private val repository: MemberRepository
) {

    fun checkAccountExists(account: String): Boolean {
        return repository.existsByAccount(account);
    }

    fun registerMember(member: Member): Boolean {
        try {
            member.password = BCrypt.hashpw(member.password, BCrypt.gensalt())
            repository.saveAndFlush(member)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun login(account: String, passwd: String): Boolean {
        try {
            val member = repository.findByAccount(account).getOrNull() ?: return false
            return BCrypt.checkpw(passwd, member.password)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun login2(account: String, passwd: String): Boolean {
        try {
            val member = Member()
            member.account = account
            val example = Example.of(member)

            if (repository.exists(example)) { // SELECT * FROM member WHERE account = xxx
                val dbMember = repository.findAll(example).first()
                return BCrypt.checkpw(passwd, dbMember.password)
            } else {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun login3(account: String, passwd: String): Member? {
        try {
            val member = repository.findByAccount(account).getOrNull() ?: return null
            if (BCrypt.checkpw(passwd, member.password)) {
                return member
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}