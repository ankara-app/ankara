package io.ankara.ui.vaadin.main.view.setting.company.invites;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import io.ankara.domain.Invite;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 7:39 PM
 */
@UIScope
@SpringComponent
public class InvitesComponent extends Grid {

    public InvitesComponent() {
        setSizeFull();
        setContainerDataSource(new BeanItemContainer<Invite>(Invite.class));
        setColumns("timeCreated","origin","company");
        setHeightMode(HeightMode.ROW);
    }
}
