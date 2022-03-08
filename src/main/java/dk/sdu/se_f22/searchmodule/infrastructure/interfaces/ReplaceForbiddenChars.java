package dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

public class ReplaceForbiddenChars {

    public String removeForbiddenChars(String toSort){
        toSort = toSort.replaceAll("[^a-zA-Z ]","");
        return toSort;
    }

}
