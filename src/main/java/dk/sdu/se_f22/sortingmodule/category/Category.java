package dk.sdu.se_f22.sortingmodule.category;

public class Category {
    private int id;
    private String name;
    private String description;
    private int parentId;

    private String requirementFieldName;
    private String requirementValue;

    public Category(int id, String name, String description, int parentId, String requirementFieldName, String requirementValue){
        this(id, name, description, parentId);
        this.requirementFieldName = requirementFieldName;
        this.requirementValue = requirementValue;
    }

    public Category(int id, String name, String description, String requirementFieldName, String requirementValue){
        this(id, name, description);
        this.requirementFieldName = requirementFieldName;
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

    public String getRequirementFieldName() {
        return requirementFieldName;
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
                + "Fieldname: " + getRequirementFieldName() + "\n"
                + "Value: " + getRequirementValue() + "\n";
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
        if(!this.requirementFieldName.equals(casted.getRequirementFieldName())){
            return false;
        }

        return true;
    }
}
