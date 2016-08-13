package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import io.ankara.domain.Invoice;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:32 AM
 */

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InvoiceGrid extends Grid {

    @PostConstruct
    private void build(){
        setContainerDataSource(new BeanItemContainer<>(Invoice.class));
        setColumns("code", "timeCreated", "company", "customer","creator","subject");
    }
}
