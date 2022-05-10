package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sortingmodule.category.Category;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDBConnectionTest {
    protected CategoryDBConnection DBConnection;

    @BeforeEach
    void setUp(){
        DBConnection = new CategoryDBConnection();
    }

    @ParameterizedTest
    @DisplayName("Read valid category")
    @CsvFileSource(resources = "CategoriesInDatabase.csv", numLinesToSkip = 1)
    void getCategoryById(int id, String name, String description, int parentId, String value, String status) {
        Category categoryActual = DBConnection.getCategoryById(id);
        Category categoryExpected = new Category(id, name, description, parentId, status, value);

        assertEquals(categoryExpected, categoryActual);
    }

    @DisplayName("Create category without parentId")
    @Test
    void CreateCategoryTest() {
        DBConnection.createCategory("TestName", "TestDescription", "TestReqValue", 1);
        String sql = "SELECT categories.id, parent_id, name, description, fieldname, value FROM categories \n" +
                "INNER JOIN requirements_values \n" +
                "on categories.requirements_id = requirements_values.id \n" +
                "INNER JOIN requirements_fieldnames \n" +
                "on requirements_values.fieldname_id = requirements_fieldnames.id \n" +
                "WHERE name = 'TestName'";

        try (PreparedStatement queryStatement = DBConnection.connect().prepareStatement(sql)) {
            ResultSet queryResultSet = queryStatement.executeQuery();

            queryResultSet.next();

            assertAll("Should return value from database",
                    ()->assertEquals("TestName",queryResultSet.getString("name")),
                    ()->assertEquals("TestDescription",queryResultSet.getString("description")),
                    ()->assertEquals(0,queryResultSet.getInt("parent_id")),
                    ()->assertEquals("name",queryResultSet.getString("fieldname")),
                    ()->assertEquals("TestReqValue",queryResultSet.getString("value"))
            );
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @AfterEach
    public void deleteRow() {
        String sql2 = "DELETE FROM requirements_values WHERE value = 'TestReqValue'";
        String sql1 = "DELETE FROM Categories WHERE name = 'TestName'";
        try (PreparedStatement querystatement1 = DBConnection.connect().prepareStatement(sql1)) {
            querystatement1.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try(PreparedStatement querystatement2 = DBConnection.connect().prepareStatement(sql2)){
            querystatement2.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
