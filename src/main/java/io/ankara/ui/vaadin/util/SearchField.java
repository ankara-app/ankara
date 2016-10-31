package io.ankara.ui.vaadin.util;


import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:07 AM
 */
public class SearchField extends TextField {

    private BeanItemContainer container;

    public SearchField() {
        setInputPrompt("Search ...");
        addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        setIcon(FontAwesome.SEARCH);

        addTextChangeListener((FieldEvents.TextChangeListener) event -> {
            String query = event.getText().trim();

            container.removeAllContainerFilters();

            if(!query.isEmpty()){
                container.addContainerFilter(new Or(
                        new SimpleStringFilter("code",query,true,false),
                        new SimpleStringFilter("company",query,true,false),
                        new SimpleStringFilter("customer",query,true,false),
                        new SimpleStringFilter("subject",query,true,false),
                        new SimpleStringFilter("creator",query,true,false)
                ));
            }
        });
    }

    public void setContainer(BeanItemContainer container) {
        this.container = container;
    }
}
