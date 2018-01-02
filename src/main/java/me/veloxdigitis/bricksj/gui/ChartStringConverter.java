package me.veloxdigitis.bricksj.gui;

import javafx.util.StringConverter;

public class ChartStringConverter extends StringConverter<Number> {

    @Override
    public String toString(Number object) {
        return String.valueOf(object.intValue());
    }

    @Override
    public Number fromString(String string) {
        return Integer.parseInt(string);
    }

}
