package io.ankara.ui.vaadin.main.view.setting;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import io.ankara.ui.vaadin.main.ViewHeader;
import io.ankara.ui.vaadin.main.view.setting.account.PasswordChangeForm;
import io.ankara.ui.vaadin.main.view.setting.account.UserDetailsForm;
import io.ankara.ui.vaadin.main.view.setting.company.CompaniesBeanCRUDComponent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 1:33 PM
 */
@UIScope
@SpringView(name = SettingView.VIEW_NAME)
public class SettingView extends VerticalLayout implements View{
    public static final String VIEW_NAME = "Settings";

    @Inject
    private PasswordChangeForm passwordChangeForm;

    @Inject
    private UserDetailsForm userDetailsForm;

    @Inject
    private CompaniesBeanCRUDComponent companiesSettingView;

    @Inject
    private ViewHeader viewHeader;

    @PostConstruct
    private void build(){
        TabSheet settingTabs = new TabSheet();

        settingTabs.addTab(companiesSettingView,"Companies", FontAwesome.BUILDING);
        settingTabs.addTab(passwordChangeForm,"Password", FontAwesome.KEY);
        settingTabs.addTab(userDetailsForm,"Account", FontAwesome.USER);

        addComponent(settingTabs);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        viewHeader.setValue("Settings");
    }
}
