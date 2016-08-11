package io.ankara.ui.vaadin.main.view.estimate;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:03 AM
 */
@SpringView(name =EstimateView.VIEW_NAME)
public class EstimateView extends CustomComponent implements View {
    public static final String VIEW_NAME = "EstimateView";

    @PostConstruct
    private void build(){
        setCompositionRoot(new Label("Estimate view work on progress"));
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}