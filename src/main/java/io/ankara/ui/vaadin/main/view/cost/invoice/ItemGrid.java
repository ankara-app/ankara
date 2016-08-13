package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import io.ankara.domain.Item;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 12:37 AM
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemGrid extends Grid {

    @PostConstruct
    private void build() {
        setWidth("100%");
        setHeightMode(HeightMode.ROW);
        setHeightByRows(1);

        setContainerDataSource(new BeanItemContainer<>(Item.class));
        setColumns("type", "description", "quantity", "price");
    }
}
