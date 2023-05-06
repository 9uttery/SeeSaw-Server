package com._8attery.seesaw.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static com._8attery.seesaw.domain.user.role.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void findByEmail() throws Exception {
        // given
        User user = User.builder()
                .name("하노")
                .email("hojeong2747@gmail.com")
                .role(ROLE_USER)
                .agreeMarketing(true)
                .build();

        // when
        Long saveId = userRepository.save(user).getId();
        Optional<User> findUSER = userRepository.findById(saveId);

        // then
        System.out.println(findUSER.get().getEmail());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void existsByEmail() throws Exception {
        // given

        // when

        // then
    }
}