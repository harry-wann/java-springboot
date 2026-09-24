package tw.harry.springboot.spring01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tw.harry.springboot.spring01.dto.Member;

@Configuration
public class MyConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public Member test2(){
        Member member = new Member();
        member.setId(1);
        member.setPasswd(null);
        return member;
    }
}
