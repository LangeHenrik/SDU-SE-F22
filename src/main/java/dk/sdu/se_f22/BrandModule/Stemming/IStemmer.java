package dk.sdu.se_f22.BrandModule.Stemming;

public interface IStemmer {
  public String stem(String word);
  
  public String[] stem(String[] word);
}
