package io.ankara.repository;

import io.ankara.domain.Company;
import io.ankara.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 8:12 PM
 */
public interface CompanyRepository extends JpaRepository<Company,Long> {

    List<Company> findByUsers(User user);

}
