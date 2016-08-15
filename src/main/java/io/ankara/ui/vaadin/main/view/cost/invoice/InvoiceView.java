package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import io.ankara.domain.Invoice;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.events.annotation.EventBusListenerTopic;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 7:09 PM
 */
@UIScope
@SpringComponent
public class InvoiceView extends CustomComponent implements View{
    public static final String VIEW_NAME = "Invoice";
    public static final String TOPIC_SHOW = "Show Invoice";



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    @EventBusListenerTopic(topic = InvoiceView.TOPIC_SHOW)
    @EventBusListenerMethod
    private void showInvoice(Invoice invoice){

    }
}
