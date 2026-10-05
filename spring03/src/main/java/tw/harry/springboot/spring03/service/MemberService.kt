package tw.harry.springboot.spring03.service

import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import tw.harry.springboot.spring03.apis.MemberForm
import tw.harry.springboot.spring03.entity.Member
import tw.harry.springboot.spring03.repo.MemberRepo

@Service
class MemberService(
    val repo: MemberRepo
) {

    fun register(form: MemberForm): Member? {
        val account = form.account ?: return null
        System.out.println("Registering $account")
        if (repo.findByAccount(account) != null) {
            throw Exception("Account taken")
        }

        val member = Member()
        member.account = account
        member.password = BCrypt.hashpw(form.password, BCrypt.gensalt())
        member.name = form.name
        member.icon = form.iconFile?.bytes

        return repo.save(member)
    }

    fun login(account: String, password: String): Member? {
        val member = repo.findByAccount(account) ?: return null
        val encryptedPassword = member.password ?: return null
        if (BCrypt.checkpw(password, encryptedPassword)) {
            return member
        } else {
            return null
        }
    }

}