package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sortingmodule.category.Category;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDBConnection{

    private static Connection connie = null;

    private Connection connect() throws SQLException, IOException {
        connie = DBConnection.getPooledConnection();
        return connie;
    }

    private void closeConnection(){
        try {
            connie.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected int deleteCategory(int id) {
        String SQL = "DELETE FROM categories WHERE id = ?";

        int affectedrows = 0;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }
        return affectedrows;
    }

    protected List getAllCategories(){
        List<Category> tmpList = new ArrayList();
        Category tmpCategory;
        String sql = "SELECT * FROM categories";

        try(PreparedStatement queryStatement = this.connect().prepareStatement(sql)) {
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
            System.out.println(e.getMessage());
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        } finally {
            this.closeConnection();
        }

        return tmpList;
    }

    protected Category getCategoryById(int queryId){
        Category tmpCategory = null;
        String sql = "SELECT categories.id, parent_id, name, description, fieldname, value FROM categories \n" +
                "INNER JOIN requirements_values \n" +
                "on categories.requirements_id = requirements_values.id \n" +
                "INNER JOIN requirements_fieldnames \n" +
                "on requirements_values.fieldname_id = requirements_fieldnames.id \n" +
                "WHERE categories.id = ?";

        try(PreparedStatement queryStatement = this.connect().prepareStatement(sql)) {
            queryStatement.setInt(1, queryId);
            ResultSet queryResultSet = queryStatement.executeQuery();

            while(queryResultSet.next()){
                int id = queryResultSet.getInt("id");
                int parentId = queryResultSet.getInt("parent_id");
                String name = queryResultSet.getString("name");
                String description = queryResultSet.getString("description");
                String fieldname = queryResultSet.getString("fieldname");
                String value = queryResultSet.getString("value");
                tmpCategory = new Category(id, name, description, parentId, fieldname, value);
            }
            if(tmpCategory == null){
                System.out.println("Category with the id " + queryId + " wasnt found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        } finally {
            this.closeConnection();
        }

        return tmpCategory;
    }

    protected int updateName(int idToChange, String changeTo) {
        String sql = "UPDATE Categories SET name = ? WHERE id = ?";
        try (Connection conn = this.connect();
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
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Parent ID must be an integer beyond 0");
            }
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    protected int updateDescription(int idToChange, String changeTo) {
        String sql = "UPDATE Categories SET description = ? WHERE id = ?";
        try (Connection conn = this.connect();
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
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Parent ID must be an integer beyond 0");
            }
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    protected int updateParentID(int idToChange, int changeTo) {
        String sql = "UPDATE Categories SET parent_id = ? WHERE id = ?";
        try (Connection conn = this.connect();
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
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Parent ID must be an integer beyond 0");
            }
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    protected void createCategory(String name, String description, String requirementsValue, int parentID, int requirementsFieldName) {
        boolean notValid = false;

        if (name.length() < 0 && name.length() > 40)
            notValid = true;
        if (description.length() < 0)
            notValid = true;
        if (getCategoryById(parentID) == null)
            notValid = true;

        if (notValid == false) {
            try {
                int checkRows = 0;
                PreparedStatement checkRowsStatement = this.connect().prepareStatement("SELECT COUNT(*) AS rows FROM requirements_fieldnames WHERE id = ?");
                checkRowsStatement.setInt(1, requirementsFieldName);
                ResultSet rsCheckRows = checkRowsStatement.executeQuery();
                rsCheckRows.next();
                checkRows = rsCheckRows.getInt("rows");

                if (checkRows == 1) {
                    PreparedStatement createStatement = this.connect().prepareStatement(
                            "INSERT INTO requirements_values(value, fieldname_id) VALUES (?,?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    createStatement.setString(1, requirementsValue);
                    createStatement.setInt(2, requirementsFieldName);

                    createStatement.execute();
                    ResultSet rs = createStatement.getGeneratedKeys();
                    int generatedKey = 0;
                    if (rs.next()) {
                        generatedKey = rs.getInt(1);
                        PreparedStatement createCategoryStatement = this.connect().prepareStatement(
                                "INSERT INTO categories(name, description, parent_id, requirements_id) VALUES (?,?,?,?)"
                        );
                        createCategoryStatement.setString(1, name);
                        createCategoryStatement.setString(2, description);
                        createCategoryStatement.setInt(3, parentID);
                        createCategoryStatement.setInt(4, generatedKey);
                        createCategoryStatement.execute();
                    }
                } else {
                    System.out.println("There is no requirement status with ID: " + requirementsFieldName);
                }
            } catch (IOException ioEx) {
                System.out.println(ioEx.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            this.closeConnection();
        }
    }

    protected void createCategory(String name, String description, String requirementsValue, int requirementsFieldname) {
        boolean notValid = false;

        if (name.length() > 0 && name.length() > 40)
            notValid = true;
        if (description.length() < 0)
            notValid = true;

        if (notValid == false) {
            try {
                int checkRows = 0;
                PreparedStatement checkRowsStatement = this.connect().prepareStatement("SELECT COUNT(*) AS rows FROM requirements_fieldnames WHERE id = ?");
                checkRowsStatement.setInt(1, requirementsFieldname);
                ResultSet rsCheckRows = checkRowsStatement.executeQuery();
                rsCheckRows.next();
                checkRows = rsCheckRows.getInt("rows");

                if (checkRows == 1) {
                    PreparedStatement createStatement = this.connect().prepareStatement(
                            "INSERT INTO requirements_values(value, fieldname_id) VALUES (?,?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    createStatement.setString(1, requirementsValue);
                    createStatement.setInt(2, requirementsFieldname);

                    createStatement.execute();
                    ResultSet rs = createStatement.getGeneratedKeys();
                    int generatedKey = 0;
                    if (rs.next()) {
                        generatedKey = rs.getInt(1);
                        PreparedStatement createCategoryStatement = this.connect().prepareStatement(
                                "INSERT INTO categories(name, description, requirements_id) VALUES (?,?,?)"
                        );
                        createCategoryStatement.setString(1, name);
                        createCategoryStatement.setString(2, description);
                        createCategoryStatement.setInt(3, generatedKey);
                        createCategoryStatement.execute();
                    }
                } else {
                    System.out.println("There is no requirement status with ID: " + requirementsFieldname);
                }
            } catch (IOException ioEx) {
                System.out.println(ioEx.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            this.closeConnection();
        }
    }
}
