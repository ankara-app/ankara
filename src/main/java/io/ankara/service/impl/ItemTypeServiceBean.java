package io.ankara.service.impl;

import io.ankara.domain.Company;
import io.ankara.domain.Item;
import io.ankara.domain.ItemType;
import io.ankara.repository.ItemRepository;
import io.ankara.repository.ItemTypeRepository;
import io.ankara.service.ItemTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 11:32 AM
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ItemTypeServiceBean implements ItemTypeService {

    @Inject
    private ItemTypeRepository itemTypeRepository;

    @Inject
    private ItemRepository itemRepository;

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
    @Transactional
    public boolean delete(ItemType itemType) {
        Collection<Item> items = itemRepository.findAllByType(itemType);
        if(!items.isEmpty())
            throw new IllegalStateException("There are cost items of type : "+itemType);

        itemTypeRepository.delete(itemType);
        return true;
    }

}
