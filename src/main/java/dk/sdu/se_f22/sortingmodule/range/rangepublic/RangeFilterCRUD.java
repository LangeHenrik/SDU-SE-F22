package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.exceptions.*;
import dk.sdu.se_f22.sortingmodule.range.validators.Validator;
import org.w3c.dom.ranges.Range;

import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.util.List;

public class RangeFilterCRUD implements RangeFilterCRUDInterface {
    Database database = new Database();
    @Override
    public RangeFilter create(String name, String description, String productAttribute, double dbMinToSave, double dbMaxToSave) throws InvalidFilterException, InvalidFilterTypeException{
        Validator.NoNegativeValue(dbMinToSave);
        Validator.NoNegativeValue(dbMaxToSave);

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(dbMinToSave,dbMaxToSave);

        try {
            return database.create(new DoubleFilter(name, description, productAttribute, dbMinToSave, dbMaxToSave));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RangeFilter create(String name, String description, String productAttribute, long dbMinToSave, long dbMaxToSave) throws InvalidFilterException, InvalidFilterTypeException{
        Validator.NoNegativeValue(dbMinToSave);
        Validator.NoNegativeValue(dbMaxToSave);

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(dbMinToSave,dbMaxToSave);

        try {
            return database.create(new LongFilter(name, description, productAttribute, dbMinToSave, dbMaxToSave));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RangeFilter create(String name, String description, String productAttribute, Instant dbMinToSave, Instant dbMaxToSave) throws InvalidFilterException, InvalidFilterTypeException {

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(dbMinToSave,dbMaxToSave);

        try {
            return database.create(new TimeFilter(name, description, productAttribute, dbMinToSave, dbMaxToSave));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RangeFilter read(int id) throws IdNotFoundException, UnknownFilterTypeException {
        RangeFilter result = database.read(id);
        if (result == null) {
            throw new IdNotFoundException("Invalid id");
        }
        return result;
    }

    @Override
    public RangeFilter delete(int id) throws IdNotFoundException {
        RangeFilter result = database.delete(id);
        if (result==null) {
            throw new IdNotFoundException("Invalid id");
        }
        return result;
    }

    @Override
    public RangeFilter update(RangeFilter filter, String newName) throws IllegalImplementationException, SQLException, InvalidFilterTypeException {
        return update(filter, newName, filter.getDescription());
    }

    @Override
    public RangeFilter update(RangeFilter filter, String newName, String newDescription) throws IllegalImplementationException, SQLException, InvalidFilterTypeException {
        RangeFilter out = null;
        if (filter instanceof DoubleFilter doubleFilter){
            out = new DoubleFilter(
                    doubleFilter.getId(),
                    newName,
                    newDescription,
                    doubleFilter.getProductAttribute(),
                    doubleFilter.getDbMinDouble(),
                    doubleFilter.getDbMaxDouble()
            );
        }
        if (filter instanceof LongFilter longFilter){
            out = new LongFilter(
                    longFilter.getId(),
                    newName,
                    newDescription,
                    longFilter.getProductAttribute(),
                    longFilter.getDbMinLong(),
                    longFilter.getDbMaxLong()
            );
        }
        if (filter instanceof TimeFilter timeFilter){
            out = new TimeFilter(
                    timeFilter.getId(),
                    newName,
                    newDescription,
                    timeFilter.getProductAttribute(),
                    timeFilter.getDbMinInstant(),
                    timeFilter.getDbMaxInstant()
            );
        }

        // throw an exception if the filter is illegally implemented
        validateFilterImplementation(filter);

        // perform the update
        RangeFilter result = database.update(out);

        // set user min and max to keep that data
        if (filter instanceof DoubleFilter){
            try {
                result.setUserMax(filter.getUserMaxDouble());
                result.setUserMin(filter.getUserMinDouble());
            } catch (InvalidFilterTypeException e) {
                e.printStackTrace();
            }
        }
        if (filter instanceof LongFilter){
            try {
                result.setUserMax(filter.getUserMaxLong());
                result.setUserMin(filter.getUserMinLong());
            } catch (InvalidFilterTypeException e) {
                e.printStackTrace();
            }
        }
        if (filter instanceof TimeFilter){
            try {
                result.setUserMax(filter.getUserMaxInstant());
                result.setUserMin(filter.getUserMinInstant());
            } catch (InvalidFilterTypeException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public RangeFilter update(RangeFilter filter, double dbMinToSave, double dbMaxToSave) throws IllegalImplementationException, InvalidFilterTypeException, SQLException {
        validateFilterImplementation(filter);

        if (!(filter instanceof DoubleFilter)){
            throw new InvalidFilterTypeException("You cannot set a double values for a non double filter");
        }

        RangeFilter modified = new DoubleFilter(
                filter.getId(),
                filter.getName(),
                filter.getDescription(),
                filter.getProductAttribute(),
                dbMinToSave,
                dbMaxToSave
        );

        RangeFilter updated = database.update(modified);

        try {
            updated.setUserMax(filter.getUserMaxDouble());
            updated.setUserMin(filter.getUserMinDouble());
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }

        return updated;
    }

    @Override
    public RangeFilter update(RangeFilter filter, long dbMinToSave, long dbMaxToSave) throws IllegalImplementationException, InvalidFilterTypeException, SQLException {
        validateFilterImplementation(filter);

        if (!(filter instanceof LongFilter)){
            throw new InvalidFilterTypeException("You cannot set a double values for a non double filter");
        }

        RangeFilter modified = new LongFilter(
                filter.getId(),
                filter.getName(),
                filter.getDescription(),
                filter.getProductAttribute(),
                dbMinToSave,
                dbMaxToSave
        );

        RangeFilter updated = database.update(modified);

        try {
            updated.setUserMax(filter.getUserMaxLong());
            updated.setUserMin(filter.getUserMinLong());
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }

        return updated;
    }

    @Override
    public RangeFilter update(RangeFilter filter, Instant dbMinToSave, Instant dbMaxToSave) throws IllegalImplementationException, InvalidFilterTypeException, SQLException {
        validateFilterImplementation(filter);

        if (!(filter instanceof TimeFilter)){
            throw new InvalidFilterTypeException("You cannot set a double values for a non double filter");
        }

        RangeFilter modified = new TimeFilter(
                filter.getId(),
                filter.getName(),
                filter.getDescription(),
                filter.getProductAttribute(),
                dbMinToSave,
                dbMaxToSave
        );

        RangeFilter updated = database.update(modified);

        try {
            updated.setUserMax(filter.getUserMaxInstant());
            updated.setUserMin(filter.getUserMinInstant());
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }

        return updated;
    }

    /**
     * throw an exception if the filter is illegally implemented
     * @param filter the filter to validate
     * @throws IllegalImplementationException is thrown if the filter is not an instance of {@link RangeFilterClass}
     */
    private void validateFilterImplementation(RangeFilter filter) throws IllegalImplementationException {
        if(!(filter instanceof RangeFilterClass)){
            throw new IllegalImplementationException("You are not allowed to implement RangeFilter interface\n" +
                    "Get the filters by calling read");
        }
    }

    @Override
    public List<RangeFilter> readAll() {
        return database.readAllFilters();
    }
}
