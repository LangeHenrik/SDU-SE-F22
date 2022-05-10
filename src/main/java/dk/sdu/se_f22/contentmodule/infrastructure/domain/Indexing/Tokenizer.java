package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;

import java.util.*;


public class Tokenizer {

    public HTMLSite tokenizeHTMLBodyText(HTMLSite site) {
        ArrayList<String> tokens = new ArrayList<>();
        String convertedsite = site.getDocumentText();
        Database database = Database.getInstance();

        int bindestreg, komma, underscore, kolon, punktum, semikolon, mellemrum;
        bindestreg = komma = underscore = kolon = punktum = semikolon = mellemrum = 0;

        for (int i = 0; i < convertedsite.length(); i++) {
            switch(convertedsite.charAt(i)) {
                case '-':
//                    System.out.println("Bindestreg test: "+database.getPageID(site.getId()) + " - " + database.getParameterID('-'));
//                    database.executeQuery("INSERT INTO cms_usedparameters (page_id, parameter_id) VALUES ("+database.getPageID(site.getId())+", "+database.getParameterID('-')+"");
                    bindestreg++;
                    break;
                case ',':
                    komma++;
                    break;
                case '_':
                    underscore++;
                    break;
                case ':':
                    kolon++;
                    break;
                case '.':
                    punktum++;
                    break;
                case ';':
                    semikolon++;
                    break;
                case ' ':
                    mellemrum++;
                    break;


            }
        }

        System.out.println(bindestreg + " " + komma + " " + underscore + " " + kolon + " " + punktum + " " + semikolon + " " + mellemrum);

        String [] splittedStrings = convertedsite.split("["+Database.getInstance().getParameters()+"]");


        for (String word: splittedStrings){
            {
                if (word != "") {
                    tokens.add(word.toLowerCase(Locale.ROOT));
                }
            }
        }
        return new HTMLSite(site.getId(), site.getHtmlCode(), tokens);
    }
}

