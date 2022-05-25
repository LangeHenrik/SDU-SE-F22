package dk.sdu.se_f22.sharedlibrary.models;

import java.util.ArrayList;
import java.util.List;

public class Brand {
    private Integer id;
    private String name;
    private String description;
    private String founded;
    private String headquarters;
    private List<String> products;

    public Brand(Integer id, String name, String description, String founded, String headquarters, List<String> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.founded = founded;
        this.headquarters = headquarters;
        this.products = products;
    }
    public Brand(Integer id, String name, String description, String founded, String headquarters) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.founded = founded;
        this.headquarters = headquarters;
        this.products = new ArrayList<>();
    }
    public Brand(String name, String description, String founded, String headquarters, List<String> products) {
        this.name = name;
        this.description = description;
        this.founded = founded;
        this.headquarters = headquarters;
        this.products = products;
    }
    public Brand(String name, String description, String founded, String headquarters) {
        this.id = null;
        this.name = name;
        this.description = description;
        this.founded = founded;
        this.headquarters = headquarters;
        this.products = new ArrayList<>();
    }


    public Brand(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", founded='" + founded + '\'' +
                ", headquarters='" + headquarters + '\'' +
                ", products=" + products +
                '}';
    }


    @Override
    public boolean equals(Object obj) {
        Brand o = (Brand) obj;
        if (this.id == o.id)
            return true;
        else
            return false;
    }
}
