package io.ankara.ui.vaadin.main.view.setting.company;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.ui.vaadin.main.view.setting.company.invites.InvitesComponent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 7:45 PM
 */
@UIScope
@SpringComponent
public class CompaniesSettingView extends VerticalLayout {

    @Inject
    private CompaniesBeanCRUDComponent companiesCRUDComponent;

    @Inject
    private InvitesComponent invitesComponent;

    @PostConstruct
    private void build() {
//        setSizeFull();
        setMargin(true);
        setSpacing(true);

        Label companiesSeparator = new Label("Your Companies");
        companiesSeparator.addStyleName(ValoTheme.LABEL_H3);
        companiesSeparator.addStyleName(ValoTheme.LABEL_COLORED);
//        companiesSeparator.setIcon(FontAwesome.BUILDING);

        Label invitesSeparator = new Label("Pending invites");
        invitesSeparator.addStyleName(ValoTheme.LABEL_H3);
        invitesSeparator.addStyleName(ValoTheme.LABEL_COLORED);
//        invitesSeparator.setIcon(FontAwesome.ENVELOPE);

        addComponents(companiesSeparator, companiesCRUDComponent, invitesSeparator, invitesComponent);
        setExpandRatio(companiesCRUDComponent, 1);
        setExpandRatio(invitesComponent, 1);
    }
}
