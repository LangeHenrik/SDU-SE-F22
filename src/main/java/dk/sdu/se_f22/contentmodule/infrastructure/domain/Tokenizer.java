package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;

import java.util.*;


class Tokenizer {


    public static ArrayList<Token> tokenizeHTMLBodyText(HTMLSite site) {
        ArrayList<Token> tokens = new ArrayList<>();
        String convertedsite = site.getDocumentText();

        int bindestreg, komma, underscore, kolon, punktum, semikolon, mellemrum;
        bindestreg = komma = underscore = kolon = punktum = semikolon = mellemrum = 0;

        for (int i = 0; i < convertedsite.length(); i++) {
            switch(convertedsite.charAt(i)) {
                case '-':
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


        for (String s: splittedStrings){
            {
                if (s != "") {
                    tokens.add(new Token(s.toLowerCase(Locale.ROOT), site.getId()));
                }
            }
        }
        return tokens;
    }
}

