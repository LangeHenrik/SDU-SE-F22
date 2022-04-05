package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.HTMLSite;

import java.util.ArrayList;

public class MockCMSIndex implements IMockCMSIndex {


    @Override
    public void mockIndex(ArrayList<String> mocktokens) {
        for(String i: mocktokens){
            System.out.println("Token: " + mocktokens + " filtered tokens indexed");
        }
    }

    @Override
    public static ArrayList<Integer> mockSearch(ArrayList<String> searchtokens) {
        ArrayList<HTMLSite> sites = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>();
        for(String s: searchtokens){
            System.out.println("Token: " + searchtokens + " is recieved");
        }

        for (HTMLSite site: sites){
            numbers.add(site.getId());
        }

        return numbers;
    }
}
