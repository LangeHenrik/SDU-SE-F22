package dk.sdu.se_f22.SortingModule.Range;

import java.util.Map;

public class RangeSearchResultMock {
    private Map<String, Double> attributes;

    public RangeSearchResultMock(Map<String, Double> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Double> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "{" +
                "attributes=" + attributes +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RangeSearchResultMock){
            RangeSearchResultMock rm = (RangeSearchResultMock) obj;
            return this.toString().equals(rm.toString());
        }

        return super.equals(obj);
    }
}
