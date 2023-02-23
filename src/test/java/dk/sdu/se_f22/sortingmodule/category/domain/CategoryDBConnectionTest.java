package dk.sdu.se_f22.sortingmodule.category.domain;


import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sortingmodule.category.Category;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDBConnectionTest {
    protected CategoryDBConnection dBConnection;

    @BeforeEach
    void setUp(){
        dBConnection = new CategoryDBConnection();
    }

    @ParameterizedTest
    @DisplayName("Read valid category")
    @CsvFileSource(resources = "CategoriesInDatabase.csv", numLinesToSkip = 1)
    void getCategoryById(int id, String name, String description, int parentId, String value, String status) {
        Category categoryActual = dBConnection.getCategoryById(id);
        Category categoryExpected = new Category(id, name, description, parentId, status, value);

        assertEquals(categoryExpected, categoryActual);
    }

    @Test
    @DisplayName("Read all, check object type")
    void getAllCategoriesReturnType() {
        List<Category> categoriesFromDB = dBConnection.getAllCategories();

        // Returns a list
        assertTrue(categoriesFromDB instanceof List<Category>);
    }

    @Test
    @DisplayName("Read all, check its not empty")
    void getAllCategoriesIsNotEmpty() {
        List<Category> categoriesFromDB = dBConnection.getAllCategories();

        // Is the list empty
        assertFalse(categoriesFromDB.isEmpty());
    }

    @Nested
    class getAllCategories{
        int countFromDB;
        protected CategoryDBConnection dBConnection;

        @BeforeEach
        void setup(){
            dBConnection = new CategoryDBConnection();

            String sql = "SELECT COUNT(*) FROM categories";

            try (Connection conn = DBConnection.getPooledConnection();
                 PreparedStatement queryStatement = conn.prepareStatement(sql)) {
                ResultSet queryResultSet = queryStatement.executeQuery();

                queryResultSet.next();

                countFromDB = queryResultSet.getInt("count");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Test
        @DisplayName("Read all, check if contains all categories")
        void getAllCategories() {
            List<Category> categories = dBConnection.getAllCategories();
            assertEquals(countFromDB, categories.size());
        }
    }

    @Test
    @DisplayName("Read all, check valid attribute types")
    void getAllCategoriesCheckAttributeType() {
        List<Category> categories = dBConnection.getAllCategories();
        Category testCategory = categories.get(0);
        assertAll("Check attribute types",
            ()->assertInstanceOf(String.class, testCategory.getName()),
            ()->assertInstanceOf(String.class, testCategory.getDescription()),
            ()->assertInstanceOf(Integer.class, testCategory.getId())
        );
    }

    @Test
    @DisplayName("Create category")
    void CreateCategoryTest() {
        dBConnection.createCategory("TestName", "TestDescription", "TestReqValue", 1);
        String sql = "SELECT categories.id, parent_id, name, description, fieldname, value FROM categories \n" +
                "INNER JOIN requirements_values \n" +
                "on categories.requirements_id = requirements_values.id \n" +
                "INNER JOIN requirements_fieldnames \n" +
                "on requirements_values.fieldname_id = requirements_fieldnames.id \n" +
                "WHERE name = 'TestName'";

        try (Connection conn = DBConnection.getPooledConnection();
             PreparedStatement queryStatement = conn.prepareStatement(sql)) {
            ResultSet queryResultSet = queryStatement.executeQuery();

            queryResultSet.next();

            assertAll("Should return value from database",
                    ()->assertEquals("TestName",queryResultSet.getString("name")),
                    ()->assertEquals("TestDescription",queryResultSet.getString("description")),
                    ()->assertEquals(0,queryResultSet.getInt("parent_id")),
                    ()->assertEquals("name",queryResultSet.getString("fieldname")),
                    ()->assertEquals("TestReqValue",queryResultSet.getString("value"))
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Create category parentId")
    void CreateCategoryTestParentId() {
        dBConnection.createCategory("TestName", "TestDescription", "TestReqValue",1, 1);

        String sql = "SELECT categories.id, parent_id, name, description, fieldname, value FROM categories \n" +
                "INNER JOIN requirements_values \n" +
                "on categories.requirements_id = requirements_values.id \n" +
                "INNER JOIN requirements_fieldnames \n" +
                "on requirements_values.fieldname_id = requirements_fieldnames.id \n" +
                "WHERE name = 'TestName'";

        try (Connection conn = DBConnection.getPooledConnection();
             PreparedStatement queryStatement = conn.prepareStatement(sql)) {
            ResultSet queryResultSet = queryStatement.executeQuery();

            queryResultSet.next();

            assertAll("Should return value from database",
                    ()->assertEquals("TestName",queryResultSet.getString("name")),
                    ()->assertEquals("TestDescription",queryResultSet.getString("description")),
                    ()->assertEquals(1,queryResultSet.getInt("parent_id")),
                    ()->assertEquals("name",queryResultSet.getString("fieldname")),
                    ()->assertEquals("TestReqValue",queryResultSet.getString("value"))
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @DisplayName("Update category name")
    @Test
    void updateCategoryNameTest() {
        int newCategoryID = dBConnection.createCategory("TestNameFake", "TestDescription", "TestReqValue",1, 1);

        dBConnection.updateName(newCategoryID, "TestName");

        String sql = "SELECT categories.id, parent_id, name, description, fieldname, value FROM categories \n" +
                "INNER JOIN requirements_values \n" +
                "on categories.requirements_id = requirements_values.id \n" +
                "INNER JOIN requirements_fieldnames \n" +
                "on requirements_values.fieldname_id = requirements_fieldnames.id \n" +
                "WHERE name = 'TestName'";

        try (Connection conn = DBConnection.getPooledConnection();
                PreparedStatement queryStatement = conn.prepareStatement(sql)) {
            ResultSet queryResultSet = queryStatement.executeQuery();

            queryResultSet.next();

            assertAll("Should return value from database",
                    ()->assertEquals("TestName",queryResultSet.getString("name")),
                    ()->assertEquals("TestDescription",queryResultSet.getString("description")),
                    ()->assertEquals(1,queryResultSet.getInt("parent_id")),
                    ()->assertEquals("name",queryResultSet.getString("fieldname")),
                    ()->assertEquals("TestReqValue",queryResultSet.getString("value"))
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @DisplayName("Update category description")
    @Test
    void updateCategoryDescriptionTest() {
        int newCategoryID = dBConnection.createCategory("TestName", "TestDescriptionFake", "TestReqValue",1, 1);

        dBConnection.updateDescription(newCategoryID, "TestDescription");

        String sql = "SELECT categories.id, parent_id, name, description, fieldname, value FROM categories \n" +
                "INNER JOIN requirements_values \n" +
                "on categories.requirements_id = requirements_values.id \n" +
                "INNER JOIN requirements_fieldnames \n" +
                "on requirements_values.fieldname_id = requirements_fieldnames.id \n" +
                "WHERE name = 'TestName'";

        try (Connection conn = DBConnection.getPooledConnection();
             PreparedStatement queryStatement = conn.prepareStatement(sql)) {

            ResultSet queryResultSet = queryStatement.executeQuery();

            queryResultSet.next();

            assertAll("Should return value from database",
                    ()->assertEquals("TestName",queryResultSet.getString("name")),
                    ()->assertEquals("TestDescription",queryResultSet.getString("description")),
                    ()->assertEquals(1,queryResultSet.getInt("parent_id")),
                    ()->assertEquals("name",queryResultSet.getString("fieldname")),
                    ()->assertEquals("TestReqValue",queryResultSet.getString("value"))
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @DisplayName("Update category parentID")
    @Test
    void updateCategoryParentIDTest() {
        int newCategoryID = dBConnection.createCategory("TestName", "TestDescription", "TestReqValue",2, 1);

        dBConnection.updateParentID(newCategoryID, 1);

        String sql = "SELECT categories.id, parent_id, name, description, fieldname, value FROM categories \n" +
                "INNER JOIN requirements_values \n" +
                "on categories.requirements_id = requirements_values.id \n" +
                "INNER JOIN requirements_fieldnames \n" +
                "on requirements_values.fieldname_id = requirements_fieldnames.id \n" +
                "WHERE name = 'TestName'";

        try (Connection conn = DBConnection.getPooledConnection();
             PreparedStatement queryStatement = conn.prepareStatement(sql)) {
            ResultSet queryResultSet = queryStatement.executeQuery();

            queryResultSet.next();

            assertAll("Should return value from database",
                    ()->assertEquals("TestName",queryResultSet.getString("name")),
                    ()->assertEquals("TestDescription",queryResultSet.getString("description")),
                    ()->assertEquals(1,queryResultSet.getInt("parent_id")),
                    ()->assertEquals("name",queryResultSet.getString("fieldname")),
                    ()->assertEquals("TestReqValue",queryResultSet.getString("value"))
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @DisplayName("Update category requirementsValue")
    @Test
    void updateCategoryRequirementsValueTest() {
        int newCategoryID = dBConnection.createCategory("TestName", "TestDescription", "TestReqValueFake",1, 1);

        dBConnection.updateRequirementsValue(newCategoryID, "TestReqValue");

        String sql = "SELECT categories.id, parent_id, name, description, fieldname, value FROM categories \n" +
                "INNER JOIN requirements_values \n" +
                "on categories.requirements_id = requirements_values.id \n" +
                "INNER JOIN requirements_fieldnames \n" +
                "on requirements_values.fieldname_id = requirements_fieldnames.id \n" +
                "WHERE name = 'TestName'";

        try (Connection conn = DBConnection.getPooledConnection();
             PreparedStatement queryStatement = conn.prepareStatement(sql)) {
            ResultSet queryResultSet = queryStatement.executeQuery();

            queryResultSet.next();

            assertAll("Should return value from database",
                    ()->assertEquals("TestName",queryResultSet.getString("name")),
                    ()->assertEquals("TestDescription",queryResultSet.getString("description")),
                    ()->assertEquals(1,queryResultSet.getInt("parent_id")),
                    ()->assertEquals("name",queryResultSet.getString("fieldname")),
                    ()->assertEquals("TestReqValue",queryResultSet.getString("value"))
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete the created elements from database after use in test.
    @AfterEach
    public void deleteRow() {
        String sql2 = "DELETE FROM requirements_values WHERE value = 'TestReqValue'";
        String sql1 = "DELETE FROM Categories WHERE name = 'TestName'";
        try (Connection conn = DBConnection.getPooledConnection();
                PreparedStatement querystatement1 = conn.prepareStatement(sql1)) {
            querystatement1.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try(Connection conn = DBConnection.getPooledConnection();
                PreparedStatement querystatement2 = conn.prepareStatement(sql2)){
            querystatement2.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // To make sure that the connection is closed after use.
    @AfterEach
    public void close() {
        dBConnection = null;
    }
}
