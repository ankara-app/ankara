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
package io.ankara.ui.vaadin.login;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.ui.vaadin.AnkaraTheme;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;
import org.vaadin.spring.security.util.SuccessfulLoginEvent;

import javax.inject.Inject;

/**
 * Full-screen UI component that allows the user to login.
 *
 * @author Petter Holmström (petter@vaadin.com)
 */
@SpringUI(path = "/login")
@Theme(AnkaraTheme.THEME)
public class LoginUI extends UI {

    @Inject
    private LoginScreen loginScreen;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Ankara | Welcome ...");

        setContent(loginScreen);
        setSizeFull();
    }

}
