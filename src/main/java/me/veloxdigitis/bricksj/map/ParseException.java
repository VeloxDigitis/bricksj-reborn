package me.veloxdigitis.bricksj.map;

public class ParseException extends Exception {

    public ParseException(String parse) {
        super("Couldn't parse " + parse);
    }

}
