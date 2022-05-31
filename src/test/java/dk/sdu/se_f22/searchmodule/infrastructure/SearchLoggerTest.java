package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.productmodule.management.domain_persistance.BaseProduct;
import dk.sdu.se_f22.searchmodule.infrastructure.logger.SearchLogger;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchLoggerTest {

    @Test
    void logSearch() {
        // We start by truncating search logs table
        try(var connection = DBConnection.getPooledConnection();
            var stmt = connection.prepareStatement("truncate table searches CASCADE; truncate table brandsearches CASCADE; truncate table productsearches CASCADE; truncate table contentsearches CASCADE;")) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (var connection = mockStatic(DBConnection.class)) {
            connection.when(DBConnection::getPooledConnection).thenThrow(new SQLException());
            SearchLogger.logSearch("Test", new SearchHits(), List.of());
            var searches = SearchLogger.getAllSearchLogs();
            assertEquals(searches.size(), 0);
        }

        var mySearchHits = new SearchHits();
        var myProduct = new Product(TestSearchModule.createExampleBaseproduct());
        var myBrand = new Brand();
        myBrand.setId(123);

        mySearchHits.setProducts(List.of(myProduct));
        mySearchHits.setBrands(List.of(myBrand));

        SearchLogger.logSearch("Test", mySearchHits, List.of());

        var searches = SearchLogger.getAllSearchLogs();

        assertEquals(searches.size(), 2);
        assertEquals(searches.get(0).getProductCounter(), 1);
        assertEquals(searches.get(0).getBrandsCounter(), 1);
        assertEquals(searches.get(0).getSearchString(), "Test");

        var timeSearched = searches.get(0).getTimeSearched();
        var expected = "Search string: TestTime searched: " + timeSearched + "Number of brands: 1Number of products: 1Number of contents: 0BrandIDs: [123]ProductIDs: [Product{uuid="+myProduct.getUuid().toString()+", averageUserReview=123.0, price=123.0, clockspeed=0.0, weight=0.0, ean=123, size='null', category='hello', name='hello', description='hello', publishedDate=2022-05-10T07:51:31.631793829Z, expirationDate=2022-05-10T07:51:31.631793829Z, inStock=[hello]}]ContentIDs: []";
        assertEquals(expected, searches.get(0).toString());
    }
}