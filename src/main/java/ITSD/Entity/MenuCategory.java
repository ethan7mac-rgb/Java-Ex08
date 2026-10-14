package ITSD.Entity;

import ITSD.Repository.IEntityStringPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class MenuCategory implements IEntityStringPK {

    @Id
    @Column(name="MenuCategoryID")
    String id;
    String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    List<MenuItem> items = new ArrayList<>();

    public MenuCategory() {}
    public MenuCategory(String description) {
        this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "categoryID=" + id +
                ", desc=" + description;
    }
}
