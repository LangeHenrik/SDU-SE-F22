package dk.sdu.se_f22.contentmodule.infrastructure.domain.Search;

public class NoResultsFoundException extends Exception{

    public NoResultsFoundException(String message){
        super(message);
    }
}
