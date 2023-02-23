package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sortingmodule.category.Category;
import java.util.List;

public class CategoryCRUD implements CategoryCRUDInterface{
    private CategoryDBConnection DBConnection;

    public CategoryCRUD(){
        this.DBConnection = new CategoryDBConnection();
    }

    @Override
    public List<Category> getAllCategories(){
        List<Category> tmpList = DBConnection.getAllCategories();
        return tmpList;
    }

    @Override
    public Category getCategoryById(int id){
        Category tmpCategory = DBConnection.getCategoryById(id);
        return tmpCategory;
    }

    @Override
    public void updateCategory(String fieldName, String updatedString, int idToChance){
        switch (fieldName.toLowerCase()){
            case "name":
                DBConnection.updateName(idToChance, updatedString);
                break;
            case "description":
                DBConnection.updateDescription(idToChance, updatedString);
                break;
            case "requirement_value":
                DBConnection.updateRequirementsValue(idToChance, updatedString);
                break;
            default:
                System.out.println("Field name: " + fieldName + " isn't supported");
        }
    }

    @Override
    public void updateCategory(String fieldName, int updatedInt, int idToChance){
        switch (fieldName.toLowerCase()){
            case "parent_id":
                DBConnection.updateParentID(idToChance, updatedInt);
                break;
            default:
                System.out.println("Field name: " + fieldName + " isn't supported");
        }
    }

    @Override
    public int createCategory(String name, String description, String requirementsValue, int parentID, int requirementsFieldName) {
        return DBConnection.createCategory(name, description, requirementsValue, parentID, requirementsFieldName);
    }

    @Override
    public int createCategory(String name, String description, String requirementsValue, int requirementsFieldName) {
        return DBConnection.createCategory(name, description, requirementsValue, requirementsFieldName);
    }

    @Override
    public int deleteCategory(int id){
        int tmpRows = DBConnection.deleteCategory(id);
        return tmpRows;
    }

}
