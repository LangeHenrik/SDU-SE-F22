package dk.sdu.se_f22.sortingmodule.category;

public class Category {
    private int id;
    private String name;
    private String description;
    private int parentId;

    public Category(int id, String name, String description, int parentId){
        this(id, name, description);
        this.parentId = parentId;
    }

    public Category(int id, String name, String description){
        this(name, description);
        this.id = id;
    }

    public Category(String name, String description){
        this.name = name;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getParentId() {
        return parentId;
    }

    @Override
    public String toString(){
        return "-----------------------" + "\n"
                + "ID: " + getId() + "\n"
                + "Name: " + getName() + "\n"
                + "Description: " + getDescription() + "\n"
                + "ParentId: " + getParentId() + "\n";
    }
}
