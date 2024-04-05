package Controller;

import View.TextView;

public class TextController {
    private TextView view;

    public TextController(TextView view) {
        this.view = view;
    }

    public void displayText(String text) {
        view.getTextArea().setText(text);
    }
}
