package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchLoggingTest {

    @Test
    void loggingSearch() {
        try (var connection = mockStatic(DBConnection.class)) {
            connection.when(DBConnection::getPooledConnection).thenThrow(new SQLException());
            SearchLogging.loggingSearch("Test", new SearchHits(), List.of());
        }

        var mySearchHits = new SearchHits();
        var myProduct = new Product();
        var myBrand = new Brand();
        myBrand.setId(123);

        mySearchHits.setProducts(List.of(myProduct));
        mySearchHits.setBrands(List.of(myBrand));

        SearchLogging.loggingSearch("Test", mySearchHits, List.of());
    }
}