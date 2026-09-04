package com.chibachimi.springdmtools.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.markdown.Markdown;

public class HelpDialog {

    private final Dialog dialog;

    public HelpDialog(String title, String helpText) {
        Markdown markdown = new Markdown(helpText);
        dialog = new Dialog(title);

        Button buttonClose = new Button("Okay", e-> dialog.close());

        dialog.add(markdown);
        dialog.getFooter().add(buttonClose);
    }

    public Dialog getDialog() {
        return dialog;
    }
}
