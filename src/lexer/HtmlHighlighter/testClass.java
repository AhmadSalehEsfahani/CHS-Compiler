package phase1.HtmlHighlighter;

public class testClass {
    public static void main(String[] args) {
        HtmlHighlighter htmlHighlighter = new HtmlHighlighter();
        htmlHighlighter.comments("myComment-yellow");
        htmlHighlighter.integerNumbers("orange");
        htmlHighlighter.newLine();
        htmlHighlighter.newLine();
        htmlHighlighter.reservedKeyWords("txtBlueBold");
        htmlHighlighter.undefinedToken("red-error");
        htmlHighlighter.space();
        htmlHighlighter.space();
        htmlHighlighter.reservedKeyWords("txtBlueBold");
        htmlHighlighter.specialCharacters("greenItalicTxt");
        htmlHighlighter.endFile();
    }
}
