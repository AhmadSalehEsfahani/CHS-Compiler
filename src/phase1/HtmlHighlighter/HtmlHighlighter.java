package phase1.HtmlHighlighter;

import java.io.FileWriter;
import java.io.IOException;

public class HtmlHighlighter {
    private StringBuilder mainText =
            new StringBuilder("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<body>\n");

    public void reservedKeyWords(String text) {
        addTags(text, "blue", true, false);
    }

    public void Identifiers(String text) {
        addTags(text, "#7F00FF", false, false);
    }

    public void integerNumbers(String text) {
        addTags(text, "orange", false, false);
    }

    public void realNumbers(String text) {
        addTags(text, "orange", false, true);
    }

    public void stringsAndCharacters(String text) {
        addTags(text, "green", false, false);
    }

    public void specialCharacters(String text) {
        addTags(text, "green", false, true);
    }

    public void comments(String text) {
        addTags(text, "yellow", false, false);
    }

    public void operatorsAndPunctuations(String text) {
        addTags(text, "black", false, false);
    }

    public void undefinedToken(String text) {
        addTags(text, "red", false, false);
    }

    public void newLine() {
        mainText.append("<br>\n");
    }

    public void space() {
        mainText.append("<p style=\"display:inline;\"> </p>\n");
    }

    public void endFile() {
        mainText.append("\n" +
                "</body>\n" +
                "</html>\n");
        try {
            FileWriter fileWriter = new FileWriter("output.html");
            fileWriter.write(mainText.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTags(String text, String color, boolean bold, boolean italic) {
        if (bold) {
            mainText.append("<b style=\"display:inline;color:" + color + "\">" + text + "</b>\n");
        } else if (italic) {
            mainText.append("<i style=\"display:inline;color:" + color + "\">" + text + "</i>\n");

        } else {
            mainText.append("<p style=\"display:inline;color:" + color + "\">" + text + "</p>\n");

        }
    }
}
