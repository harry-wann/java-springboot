package tw.harry.springboot.spring06.service

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tw.harry.springboot.spring06.repository.MemberRepo

@Service
class MemberService(
    private val repo: MemberRepo
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {

        val member = repo.findByAccount(username).orElseThrow {
            UsernameNotFoundException("Account not found: $username!")
        }

        return User.builder()
            .username(member.account)
            .password(member.passwd)
            .roles(member.role)
            .build()
    }
}