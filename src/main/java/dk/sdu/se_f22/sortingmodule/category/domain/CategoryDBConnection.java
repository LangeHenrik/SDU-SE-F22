package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sortingmodule.category.Category;
import dk.sdu.se_f22.sortingmodule.category.CategoryCRUDInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDBConnection implements CategoryCRUDInterface {

    public static CategoryDBConnection shared = new CategoryDBConnection();
    private static String URL = "jdbc:postgresql://localhost:5432/SOM3";
    private static String username = "CategoryAdmin";
    private static String password = "CategoryAdmin";
    private static Connection connie = null;

    public Connection connect(){
        try{
            DriverManager.registerDriver(new org.postgresql.Driver());
            connie = DriverManager.getConnection(this.URL,this.username,this.password);
            return connie;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connie;
    }

    public void closeConnection(){
        try {
            connie.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int deleteCategory(int id) {
        String SQL = "DELETE FROM categories WHERE id = ?";

        int affectedrows = 0;

        try (Connection conn = this.shared.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }

    public List getAllCategories(){
        List<Category> tmpList = new ArrayList();
        Category tmpCategory;
        String sql = "SELECT * FROM categories";

        try(PreparedStatement queryStatement = this.shared.connect().prepareStatement(sql)) {
            ResultSet queryResultSet = queryStatement.executeQuery();

            while(queryResultSet.next()){
                int id = queryResultSet.getInt("id");
                int parentId = queryResultSet.getInt("parent_id");
                String name = queryResultSet.getString("name");
                String description = queryResultSet.getString("description");

                tmpCategory = new Category(id, name, description, parentId);
                tmpList.add(tmpCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.shared.closeConnection();
        }

        return tmpList;
    }

    public Category getCategoryById(int queryId){
        Category tmpCategory = null;
        String sql = "SELECT categories.id, parent_id, name, description, value, condition FROM categories\n" +
                "INNER JOIN requirements\n" +
                "on categories.requirements_id = requirements.id\n" +
                "INNER JOIN requirements_status \n" +
                "on requirements.status_id = requirements_status.id\n" +
                "WHERE categories.id = ?";

        try(PreparedStatement queryStatement = this.shared.connect().prepareStatement(sql)) {
            queryStatement.setInt(1, queryId);
            ResultSet queryResultSet = queryStatement.executeQuery();

            while(queryResultSet.next()){
                int id = queryResultSet.getInt("id");
                int parentId = queryResultSet.getInt("parent_id");
                String name = queryResultSet.getString("name");
                String description = queryResultSet.getString("description");
                String status = queryResultSet.getString("condition");
                String value = queryResultSet.getString("value");
                tmpCategory = new Category(id, name, description, parentId, status, value);
            }
            if(tmpCategory == null){
                System.out.println("Category with the id " + queryId + " wasnt found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.shared.closeConnection();
        }

        return tmpCategory;
    }

    public int updateName(int idToChange, String changeTo) {
        String sql = "UPDATE Categories SET name = ? WHERE id = ?";
        try (Connection conn = CategoryDBConnection.shared.connect();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {
            if (changeTo.length() > 0) {
                try {
                    pStatement.setString(1,changeTo);
                    pStatement.setInt(2,idToChange);

                    int affectedRows = pStatement.executeUpdate();
                    if (affectedRows == 0) {
                        System.out.println("That ID does not exist");
                    }
                    return affectedRows;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Parent ID must be an integer beyond 0");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public int updateDescription(int idToChange, String changeTo) {
        String sql = "UPDATE Categories SET description = ? WHERE id = ?";
        try (Connection conn = CategoryDBConnection.shared.connect();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {
            if (changeTo.length() > 0) {
                try {
                    pStatement.setString(1,changeTo);
                    pStatement.setInt(2,idToChange);

                    int affectedRows = pStatement.executeUpdate();
                    if (affectedRows == 0) {
                        System.out.println("That ID does not exist");
                    }
                    return affectedRows;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Parent ID must be an integer beyond 0");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public int updateParentID(int idToChange, int changeTo) {
        String sql = "UPDATE Categories SET parent_id = ? WHERE id = ?";
        try (Connection conn = CategoryDBConnection.shared.connect();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {
            if (changeTo > 0) {
                try {
                    pStatement.setInt(1,changeTo);
                    pStatement.setInt(2,idToChange);

                    int affectedRows = pStatement.executeUpdate();
                    if (affectedRows == 0) {
                        System.out.println("That ID does not exist");
                    }
                    return affectedRows;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Parent ID must be an integer beyond 0");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public void createCategory(String name, String description, String requirementsValue, int parentID, int requirementsStatus) {
        try {
            int checkRows = 0;
            PreparedStatement checkRowsStatement = this.shared.connect().prepareStatement("SELECT COUNT(*) AS rows FROM requirements_status WHERE id = ?");
            checkRowsStatement.setInt(1, requirementsStatus);
            ResultSet rsCheckRows = checkRowsStatement.executeQuery();
            rsCheckRows.next();
            checkRows = rsCheckRows.getInt("rows");

            if (checkRows == 1) {
                PreparedStatement createStatement = this.shared.connect().prepareStatement(
                        "INSERT INTO requirements(value, status_id) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                createStatement.setString(1, requirementsValue);
                createStatement.setInt(2, requirementsStatus);

                createStatement.execute();
                ResultSet rs = createStatement.getGeneratedKeys();
                int generatedKey = 0;
                if (rs.next()) {
                    generatedKey = rs.getInt(1);
                    PreparedStatement createCategoryStatement = this.shared.connect().prepareStatement(
                            "INSERT INTO categories(name, description, parent_id, requirements_id) VALUES (?,?,?,?)"
                    );
                    createCategoryStatement.setString(1, name);
                    createCategoryStatement.setString(2, description);
                    createCategoryStatement.setInt(3, parentID);
                    createCategoryStatement.setInt(4, generatedKey);
                    createCategoryStatement.execute();
                }
            } else {
                System.out.println("There is no requirement status with ID: " + requirementsStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CategoryDBConnection.shared.closeConnection();
    }

    public void createCategory(String name, String description, String requirementsValue, int requirementsStatus) {
        try {
            int checkRows = 0;
            PreparedStatement checkRowsStatement = this.shared.connect().prepareStatement("SELECT COUNT(*) AS rows FROM requirements_status WHERE id = ?");
            checkRowsStatement.setInt(1, requirementsStatus);
            ResultSet rsCheckRows = checkRowsStatement.executeQuery();
            rsCheckRows.next();
            checkRows = rsCheckRows.getInt("rows");

            if (checkRows == 1) {
                PreparedStatement createStatement = this.shared.connect().prepareStatement(
                        "INSERT INTO requirements(value, status_id) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                createStatement.setString(1, requirementsValue);
                createStatement.setInt(2, requirementsStatus);

                createStatement.execute();
                ResultSet rs = createStatement.getGeneratedKeys();
                int generatedKey = 0;
                if (rs.next()) {
                    generatedKey = rs.getInt(1);
                    PreparedStatement createCategoryStatement = this.shared.connect().prepareStatement(
                            "INSERT INTO categories(name, description, requirements_id) VALUES (?,?,?)"
                    );
                    createCategoryStatement.setString(1, name);
                    createCategoryStatement.setString(2, description);
                    createCategoryStatement.setInt(3, generatedKey);
                    createCategoryStatement.execute();
                }
            } else {
                System.out.println("There is no requirement status with ID: " + requirementsStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CategoryDBConnection.shared.closeConnection();
    }

}
