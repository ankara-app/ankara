package io.ankara.ui.vaadin.util;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
public class NotificationUtils {

    public static void show(String message, String description, Notification.Type type) {
        Notification notification = new Notification(message,description,type,true);
        notification.setDelayMsec(-1);
        notification.setStyleName(ValoTheme.NOTIFICATION_WARNING+" "+ValoTheme.NOTIFICATION_CLOSABLE);
        notification.show(Page.getCurrent());
    }
}
