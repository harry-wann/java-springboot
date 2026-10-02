package tw.harry.springboot.spring02.service

import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.harry.springboot.spring02.entity.Info
import tw.harry.springboot.spring02.entity.Member
import tw.harry.springboot.spring02.repository.InfoRepo
import tw.harry.springboot.spring02.repository.MemberRepo
import kotlin.jvm.optionals.getOrNull

@Service
class MemberService(
    @Autowired
    val memberRepo: MemberRepo,
    @Autowired
    val infoRepo: InfoRepo
) {

    @Transactional
    fun save(member: Member, info: Info): Member {
        member.passwd = BCrypt.hashpw(member.passwd, BCrypt.gensalt())
        member.setInfo(info)
        return memberRepo.save(member)
    }

    @Transactional
    fun saveInfoToMember(memberId: Long, info: Info): Info? {
        val member = memberRepo.findById(memberId).getOrNull()
        if (member != null) {
            val dbInfo = member.info
            if (dbInfo != null) {
                info.id = memberId
            }
            member.setInfo(info)
            val savedMember = memberRepo.save(member)
            return savedMember.info
        }
        return null
    }

    fun findMemberById(memberId: Long): Member? {
        return memberRepo.findById(memberId).get()
    }
}