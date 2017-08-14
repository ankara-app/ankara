package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.ItemType;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 11:21 AM
 */
public interface ItemTypeService {

    boolean save(ItemType itemType);

    List<ItemType> getItemTypes(Company company);

    ItemType getItemType(String name);

    boolean delete(ItemType itemType);
}
