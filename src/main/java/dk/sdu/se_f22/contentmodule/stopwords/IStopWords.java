package dk.sdu.se_f22.contentmodule.stopwords;

import java.util.List;

public interface IStopWords {
	List<String> filter(List<String> tokens);
}
