package dk.sdu.se_f22.searchmodule.infrastructure.mocks;

public class MockIndexingData {
    String name;

    public MockIndexingData(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;
        MockIndexingData other = (MockIndexingData) obj;

        return name.equals(other.name);
    }
}
