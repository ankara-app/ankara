package io.ankara.repository;

import io.ankara.domain.Item;
import io.ankara.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 10/3/16.
 */
public interface ItemRepository extends JpaRepository<Item,Long>{
    List<Item> findAllByType(ItemType type);
}
