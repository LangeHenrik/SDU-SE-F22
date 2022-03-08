package dk.sdu.se_f22.sortingmodule.range;

/** This is the dataclass for range filters
 */
public class RangeFilter {
    private int id;
    private double min;
    private double max;

    /** Creates a dataclass for RangeFilters
     *
     * @param id the id for the filter stored
     * @param min the lower bound for the values accepted
     * @param max the upper bound for the values accepted
     */
    public RangeFilter(int id, double min, double max) {
        this.id = id;
        this.min = min;
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
