package io.ankara.ui.vaadin.util;

import com.vaadin.sass.internal.util.StringUtil;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
public class NotificationUtils {

    public static void show(String message, String description,Notification.Type type, String... styles) {
        Notification notification = new Notification(message,description,type,true);
        notification.setDelayMsec(-1);
        notification.setStyleName(StringUtils.join(styles," "));
        notification.show(Page.getCurrent());
    }

    public static void showWarning(String message, String description){
        show(message,description,Notification.Type.WARNING_MESSAGE,ValoTheme.NOTIFICATION_WARNING,ValoTheme.NOTIFICATION_CLOSABLE);
    }

    public static void showSuccess(String message, String description) {
        show(message,description,Notification.Type.HUMANIZED_MESSAGE,ValoTheme.NOTIFICATION_CLOSABLE);
    }
}
