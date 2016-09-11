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
package io.ankara.ui.vaadin.welcome;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.main.view.cost.invoice.InvoicesView;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;

/**
 * Full-screen UI component that welcomes users.
 *
 * @author Petter Holmström (petter@vaadin.com)
 */
@SpringUI(path = "/welcome")
@Theme(AnkaraTheme.THEME)
public class WelcomeUI extends UI {

    @Inject
    private ApplicationContext applicationContext;

    @Inject
    private UserService userService;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("ankara | Welcome ...");

        if (userService.isCurrentUserAuthenticated())
            getPage().setLocation("/app");
        else {
            setContent(applicationContext.getBean(WelcomeScreen.class));
            setSizeFull();
        }
    }

}
