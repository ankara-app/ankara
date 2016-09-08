package io.ankara.ui.vaadin.util;

import com.vaadin.event.Action;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextField;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/8/16.
 */
public class TextFieldUtils {
    /**
     * Handles the ENTER key while the TextField has focus.
     * See https://vaadin.com/forum/#!/thread/77601/7736664
     * See http://ramontalaverasuarez.blogspot.co.at/2014/06/vaadin-7-detect-enter-key-in-textfield.html
     */
    public static void handleEnter(AbstractTextField searchField, Action.Listener listener) {
        ShortcutListener enterShortCut = new ShortcutListener(
                "EnterShortcut", ShortcutAction.KeyCode.ENTER, null) {
            private static final long serialVersionUID = -2267576464623389044L;
            @Override
            public void handleAction(Object sender, Object target) {
                listener.handleAction(sender, target);
            }
        };
        handleShortcut(searchField, enterShortCut);
    }

    /**
     * Lets the ShortcutListener handle the action while the TextField has focus.
     */
    public static void handleShortcut(AbstractTextField textField, ShortcutListener shortcutListener) {
        textField.addFocusListener((FieldEvents.FocusEvent event) -> textField.addShortcutListener(shortcutListener));
        textField.addBlurListener((FieldEvents.BlurEvent event) -> textField.removeShortcutListener(shortcutListener));
    }
}
