package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.ItemType;
import io.ankara.repository.ItemTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 11:32 AM
 */
@Service
public class ItemTypeServiceBean implements ItemTypeService {

    @Inject
    private ItemTypeRepository itemTypeRepository;

    @Override
    @Transactional
    public boolean save(ItemType itemType) {
        itemTypeRepository.save(itemType);
        return true;
    }

    @Override
    public List<ItemType> getItemTypes(Company company) {
        return itemTypeRepository.findByCompany(company);
    }

    @Override
    public ItemType getItemType(String name) {
        return itemTypeRepository.findByName(name);
    }

    @Override
    public boolean delete(ItemType itemType) {
        itemTypeRepository.delete(itemType);
        return true;
    }

}
