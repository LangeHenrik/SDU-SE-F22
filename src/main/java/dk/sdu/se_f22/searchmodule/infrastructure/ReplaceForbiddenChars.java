package dk.sdu.se_f22.searchmodule.infrastructure;

public class ReplaceForbiddenChars {

    public String removeForbiddenChars(String toSort){
        toSort = toSort.replaceAll("[^0-9a-zA-Z ]","");
        return toSort;
    }

}
