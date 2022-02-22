package dk.sdu.se_f22.Bim.src.models;

import java.util.ArrayList;

public class Brand {
    private String name;
    private String description;
    private String founded;
    private String headquarters;
    private ArrayList<String> products;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFounded() {
        return founded;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFounded(String founded) {
        this.founded = founded;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }
}
