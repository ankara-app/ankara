package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import io.ankara.domain.Invoice;

import javax.annotation.PostConstruct;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:32 AM
 */

@UIScope
@SpringComponent
public class InvoicesGrid extends Grid {

    @PostConstruct
    private void build(){
        setContainerDataSource(new BeanItemContainer<>(Invoice.class));
        setColumns("code", "timeCreated", "company", "customer","creator","subject");
    }
}
