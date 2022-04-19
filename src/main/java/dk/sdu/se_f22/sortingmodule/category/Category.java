package dk.sdu.se_f22.sortingmodule.category;

public class Category {
    private int id;
    private String name;
    private String description;
    private int parentId;

    private String requirementStatus;
    private String requirementValue;

    public Category(int id, String name, String description, int parentId, String requirementStatus, String requirementValue){
        this(id, name, description, parentId);
        this.requirementStatus = requirementStatus;
        this.requirementValue = requirementValue;
    }

    public Category(int id, String name, String description, String requirementStatus, String requirementValue){
        this(id, name, description);
        this.requirementStatus = requirementStatus;
        this.requirementValue = requirementValue;
    }

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

    public String getRequirementStatus() {
        return requirementStatus;
    }

    public String getRequirementValue() {
        return requirementValue;
    }

    @Override
    public String toString(){
        return "-----------------------" + "\n"
                + "ID: " + getId() + "\n"
                + "Name: " + getName() + "\n"
                + "Description: " + getDescription() + "\n"
                + "ParentId: " + getParentId() + "\n"
                + "Value: " + getRequirementValue() + "\n"
                + "Status: " + getRequirementStatus() + "\n";
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Category casted)){
            return false;
        }
        if(this.id != casted.getId()){
            return false;
        }
        if(!this.name.equals(casted.getName())){
            return false;
        }
        if(!this.description.equals(casted.getDescription())){
            return false;
        }
        if(this.parentId != casted.getParentId()){
            return false;
        }
        if(!this.requirementValue.equals(casted.getRequirementValue())){
            return false;
        }
        if(!this.requirementStatus.equals(casted.getRequirementStatus())){
            return false;
        }

        return true;
    }
}
