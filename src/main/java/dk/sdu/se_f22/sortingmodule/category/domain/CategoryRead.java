package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sortingmodule.category.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRead {
    public static CategoryRead shared = new CategoryRead();
    private CategoryDBConnection db = CategoryDBConnection.shared;

    public List getAllCategories(){
        List<Category> tmpList = new ArrayList();
        Category tmpCategory;

        try {
            PreparedStatement queryStatement = db.connect().prepareStatement("SELECT * FROM categories");
            ResultSet queryResultSet = queryStatement.executeQuery();

            while(queryResultSet.next()){
                int id = Integer.parseInt(queryResultSet.getString("id"));
                String name = queryResultSet.getString("name");
                String description = queryResultSet.getString("description");
                if(queryResultSet.getString("parent_id") != null){
                    int parentId = Integer.parseInt(queryResultSet.getString("parent_id"));
                    tmpCategory = new Category(id, name, description, parentId);
                    tmpList.add(tmpCategory);
                } else {
                    tmpCategory = new Category(id, name, description);
                    tmpList.add(tmpCategory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }

        return tmpList;
    }

    public Category getCategoryById(int queryId){
        Category tmpCategory = null;

        try {
            PreparedStatement queryStatement = db.connect().prepareStatement("SELECT * FROM categories WHERE id = ?");
            queryStatement.setInt(1, queryId);
            ResultSet queryResultSet = queryStatement.executeQuery();

            while(queryResultSet.next()){
                // int id = Integer.parseInt(queryResultSet.getString("id"));
                int id = queryResultSet.getInt("id");
                int parentId = queryResultSet.getInt("parent_id");
                String name = queryResultSet.getString("name");
                String description = queryResultSet.getString("description");
                /*if(queryResultSet.getString("parent_id") != null){
                    int parentId = Integer.parseInt(queryResultSet.getString("parent_id"));
                    tmpCategory = new Category(id, name, description, parentId);
                } else {
                    tmpCategory = new Category(id, name, description);
                }*/
                tmpCategory = new Category(id, name, description, parentId);
            }
            if(tmpCategory == null){
                System.out.println("Category with the id " + queryId + " wasnt found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }

        return tmpCategory;
    }
}
