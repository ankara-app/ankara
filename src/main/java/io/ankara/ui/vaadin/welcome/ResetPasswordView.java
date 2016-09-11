package io.ankara.ui.vaadin.welcome;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import static io.ankara.ui.vaadin.welcome.LoginForm.VIEW_NAME;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
@UIScope
@SpringView(name = VIEW_NAME)
@SpringComponent
public class ResetPasswordView implements View {
    public static final String VIEW_NAME = "ResetPassword";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
