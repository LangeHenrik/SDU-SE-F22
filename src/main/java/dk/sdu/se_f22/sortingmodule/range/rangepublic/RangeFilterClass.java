package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;

import java.time.Instant;
import java.util.Collection;

/**
 * Intentionally default
 */
abstract class RangeFilterClass implements RangeFilter{
    private final int ID;
    private final String NAME;
    private final String DESCRIPTION;
    private final String PRODUCT_ATTRIBUTE;

    public RangeFilterClass(int ID, String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE) {
        this.ID = ID;
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.PRODUCT_ATTRIBUTE = PRODUCT_ATTRIBUTE;
    }

    //todo test this
    public abstract Collection<RangeSearchResultMock> useFilter(Collection<RangeSearchResultMock> inputs);

    @Override
    public boolean equals(Object other) {
        //todo test this
        if(!(other instanceof RangeFilterClass casted)){
            return false;
        }

        if (this.ID != casted.getId()) {
            return false;
        }
        if (!this.NAME.equals(casted.getName())) {
            return false;
        }
        if (!this.DESCRIPTION.equals(casted.getDescription())) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (!this.PRODUCT_ATTRIBUTE.equals(casted.getProductAttribute())) {
            return false;
        }
        return true;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getProductAttribute() {
        return PRODUCT_ATTRIBUTE;
    }


    //Below are the methods which are specialization dependent
    //todo test these in their specific implemenatations
    @Override
    public double getDbMinDouble() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant getDbMinInstant() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long getDbMinLong() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }

    @Override
    public double getDbMaxDouble() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant getDbMaxInstant() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long getDbMaxLong() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }

    @Override
    public double getUserMinDouble() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant getUserMinInstant() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long getUserMinLong() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }

    @Override
    public double getUserMaxDouble() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant getUserMaxInstant() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long getUserMaxLong() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }

    @Override
    public double setUserMin(double userMin) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant setUserMin(Instant userMin) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long setUserMin(long userMin) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }

    @Override
    public double setUserMax(double userMax) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant setUserMax(Instant userMax) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long setUserMax(long userMax) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }
}
