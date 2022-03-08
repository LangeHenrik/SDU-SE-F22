package dk.sdu.se_f22.sortingmodule.range;

public class DBRangeFilter{
    private int id;
    private String description;
    private String name;
    private String productAttribute;
    private double min;
    private double max;

    public DBRangeFilter(int id, String description, String name, String productAttribute, double min, double max){
        this(description, name, productAttribute, min, max);
        this.setId(id);
    }

    public DBRangeFilter(String description, String name, String productAttribute, double min, double max) {
        this.description = description;
        this.name = name;
        this.productAttribute = productAttribute;
        this.min = min;
        this.max = max;
    }

    public boolean equals(DBRangeFilter other) {
        if (this.id != other.getId()) {
            return false;
        }
        if (this.name != other.getName()) {
            return false;
        }
        if (this.description != other.getDescription()) {
            return false;
        }
        if (this.productAttribute != other.getProductAttribute()) {
            return false;
        }
        if (this.min != other.getMin()) {
            return false;
        }
        if (this.max != other.getMax()) {
            return false;
        }
        return true;
    }

    public int getId() {
        return this.id;
    }

    public int setId(int id){
        return this.id = id;
    }

    public String getDescription() { return this.description; }

    public String getName() {
        return this.name;
    }

    public String getProductAttribute() {
        return this.productAttribute;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

}
