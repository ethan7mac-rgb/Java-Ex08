package ITSD.Entity;

import ITSD.Repository.IEntityIntPK;
import jakarta.persistence.*;

@Entity
public class MenuItem implements IEntityIntPK {

    @Id
    @Column(name="MenuItemID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name="MenuCategoryID")
    MenuCategory category;

    String description;
    double price;
    boolean vegetarian;

    public MenuItem() {

    }
    public MenuItem(MenuCategory category, String description, double price, boolean vegetarian) {
        this.category = category;
        this.description = description;
        this.price = price;
        this.vegetarian = vegetarian;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }
    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public MenuCategory getCategory() {
        return category;
    }
    public void setCategory(MenuCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "itemID=" + id +
                ", desc=" + description;
    }
}
