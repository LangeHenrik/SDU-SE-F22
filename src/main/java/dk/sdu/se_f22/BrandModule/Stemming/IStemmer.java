package dk.sdu.se_f22.BrandModule.Stemming;

import java.util.ArrayList;
import java.util.List;

public interface IStemmer {
  public String stem(String word);
  
  public ArrayList<String> stem(List<String> word);
}
