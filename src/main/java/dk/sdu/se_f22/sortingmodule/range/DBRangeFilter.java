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

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof DBRangeFilter)){
            return false;
        }

        DBRangeFilter casted = (DBRangeFilter) other;

        if (this.id != casted.getId()) {
            return false;
        }
        if (this.name != casted.getName()) {
            return false;
        }
        if (this.description != casted.getDescription()) {
            return false;
        }
        if (this.productAttribute != casted.getProductAttribute()) {
            return false;
        }
        if (this.min != casted.getMin()) {
            return false;
        }
        if (this.max != casted.getMax()) {
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
