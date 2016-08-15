package io.ankara.repository;

import io.ankara.domain.Company;
import io.ankara.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 11:33 AM
 */
public interface ItemTypeRepository extends JpaRepository<ItemType,Long> {
    List<ItemType> findByCompany(Company company);

    ItemType findByName(String name);
}
