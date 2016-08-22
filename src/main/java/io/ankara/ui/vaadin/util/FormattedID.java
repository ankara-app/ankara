package io.ankara.ui.vaadin.util;

import java.io.Serializable;

/**
 * A String formatted ID wrapper class
 * Used to generate ID which have been formated to use 0--9 and A -- Z
 * with user preference separators such as -,|/ etc
 * <p>
 * Author: Boniface Chacha <bonifacechacha@gmail.com>
 * Date: 7/25/12
 * Time: 1:49 PM
 */

public class FormattedID implements Serializable {

    /**
     * Generate an ID
     *
     * @return generated ID
     * @throws FormatExhaustedException when the numbers to be generated from the specified format are over
     */
    public static String generate(String lastGeneratedID) throws IndexOutOfBoundsException {
        String currentGenerateID = new String(lastGeneratedID);
        for (int index = lastGeneratedID.length() - 1; index >= 0; index--) {
            char indexedChar = currentGenerateID.charAt(index);

            if (Character.isLetter(indexedChar) && !isLastLetterChar(indexedChar)) {
                currentGenerateID = incrementChar(currentGenerateID, index);
                return currentGenerateID;
            } else if (Character.isDigit(indexedChar) && !isLastIntChar(indexedChar)) {
                currentGenerateID = incrementInt(currentGenerateID, index);
                return currentGenerateID;
            }

            currentGenerateID = resetStart(currentGenerateID, index);
        }

        return currentGenerateID;
    }

    private static String incrementChar(String currentGenerateID, int position) {
        Character subject = getSubject(currentGenerateID, position);
        return setOnID(currentGenerateID, position, ++subject);
    }

    private static String incrementInt(String currentGenerateID, int position) {
        Character subject = getSubject(currentGenerateID, position);
        return setOnID(currentGenerateID, position, ++subject);
    }

    private static boolean isLastLetterChar(Character ch) {
        return ch.equals('Z');
    }

    private static boolean isLastIntChar(Character ch) {
        return ch.equals('9');
    }

    private static Character getSubject(String currentGenerateID, int position) {
        return currentGenerateID.charAt(position);
    }

    private static String setOnID(String currentGenerateID, int position, Character i) {
        String left = currentGenerateID.substring(0, position);
        String right = currentGenerateID.substring(position + 1);
        currentGenerateID = left + i + right;

        return currentGenerateID;
    }

    private static String resetStart(String currentGenerateID, int index) {
        for (int ind = index; ind < currentGenerateID.length(); ind++) {
            currentGenerateID = resetAt(currentGenerateID, ind);
        }
        return currentGenerateID;
    }

    private static String resetAt(String currentGenerateID, int ind) {
        if (Character.isLetter(currentGenerateID.charAt(ind))) {
            return setOnID(currentGenerateID, ind, 'A');
        } else if (Character.isDigit(currentGenerateID.charAt(ind))) {
            return setOnID(currentGenerateID, ind, '0');
        } else return currentGenerateID;
    }

}
