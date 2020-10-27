package cn.tedu.straw.portal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class SecurityTests {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void hehe(){
        String rawPassword="1234";
        String encodePassword=passwordEncoder.encode(rawPassword);
        log.debug("原密码,{} 新密码{}",rawPassword,encodePassword);
    }

}
