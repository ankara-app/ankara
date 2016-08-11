package io.ankara.repository;

import io.ankara.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 6:48 PM
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
