package com.devculi.sway.sharedmodel.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class BaseRuntimeException extends RuntimeException {
    private String messageKey;
    private List<String> messageKeyCollection;
    private List<String> messageDescCollection;
    private ResourceBundle resourceBundle = null;

    public ResourceBundle getResourceBundle(Locale locale) {
        if (this.resourceBundle == null) {
            Locale.setDefault(new Locale(locale.getLanguage(), locale.getCountry()));
            this.resourceBundle = ResourceBundle.getBundle("messages");
        }
        return this.resourceBundle;
    }

    public List<String> getExceptionMessage() {
        if (this.messageDescCollection == null) {
            this.messageDescCollection = new ArrayList<String>();
        }
        return messageDescCollection;
    }

}
