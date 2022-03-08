package dk.sdu.se_f22.sharedlibrary.models;

import java.util.ArrayList;

public class Brand {
    private int id;
    private String name;
    private String description;
    private String founded;
    private String headquarters;
    private ArrayList<String> products;

    public Brand(){
        //TODO Fix this
    }

    public Brand(int id, String name, String description, String founded, String headquarters, ArrayList<String> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.founded = founded;
        this.headquarters = headquarters;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFounded() {
        return founded;
    }

    public void setFounded(String founded) {
        this.founded = founded;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }
}
