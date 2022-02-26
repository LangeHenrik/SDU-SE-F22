package dk.sdu.se_f22.SortingModule.Range;

public interface RangeFilterInterface {
    /** Validates whether a value is inside the range defined by this filter
     * @param v
     * @return true if within range else false
     */
    boolean validate(double v);
}
