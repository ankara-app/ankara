package io.ankara.repository;

import io.ankara.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/12/16.
 */
public interface TokenRepository  extends JpaRepository<Token, String> {
}
