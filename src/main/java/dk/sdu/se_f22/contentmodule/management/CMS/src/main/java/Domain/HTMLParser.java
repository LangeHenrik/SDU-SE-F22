package Domain;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class HTMLParser {
    private File inputFile;
    private Document parsedHTML;

    public HTMLParser(String fileName) throws IOException { //Using the filename and the parseHTML(), we are constructing a parsed HTML page
        this.inputFile = new File(fileName);
        parseHTML();
    }

    private void parseHTML() throws IOException { //Jsoup.parse() uses an input file and charset, to parse the file
        parsedHTML = Jsoup.parse(inputFile, "UTF-8");
    }

    public Document getParsedHTML() { //Here we return the parsed HTML
        return parsedHTML;
    }
}