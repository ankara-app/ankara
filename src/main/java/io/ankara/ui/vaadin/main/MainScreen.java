/*
 * Copyright 2015 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ankara.ui.vaadin.main;

import com.vaadin.navigator.Navigator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import io.ankara.ui.vaadin.main.view.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.invoice.InvoicesView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Full-screen UI component that allows the user to navigate between views, and log out.
 *
 * @author Petter Holmström (petter@vaadin.com)
 */
@UIScope
@SpringComponent
public class MainScreen extends CustomComponent {

    @Inject
    private SpringViewProvider springViewProvider;

    @Inject
    private MainHeader mainHeader;

    @Inject
    private ViewHeader viewHeader;

    @Inject
    private MainUI mainUI;

    @PostConstruct
    private void build(){
        setSizeFull();

        VerticalLayout root =new VerticalLayout();
        root.setSizeFull();
        root.setMargin(new MarginInfo(false,true,true,true));
        setCompositionRoot(root);

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(false);
//        content.setHeight("100%");
        content.setSizeFull();

        content.addComponents(mainHeader,viewHeader);

        VerticalLayout viewContainer = new VerticalLayout();
//        viewContainer.setWidth("100%");
        viewContainer.setSizeFull();
        viewContainer.setMargin(false);
        content.addComponent(viewContainer);
        content.setExpandRatio(viewContainer,1);

        root.addComponent(content);
        root.setComponentAlignment(content, Alignment.MIDDLE_CENTER);

        Navigator navigator = new Navigator(mainUI, viewContainer);
        navigator.addProvider(springViewProvider);
        navigator.navigateTo(InvoicesView.VIEW_NAME);
    }

}
