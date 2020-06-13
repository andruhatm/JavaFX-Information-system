package sample.Filters;

import javafx.scene.control.TextField;

public class NumberTextFilter extends TextField {

    public NumberTextFilter() {
        this.setPromptText("вводите числа");
    }

    // allows user to enter only pre-prepared symbols in custom TextField
    public void replaceText(int i, int i1, String string) {
        if (string.matches("^[0-9[\b]]")||string.isEmpty()) {
            super.replaceText(i, i1, string);
        }
    }

}