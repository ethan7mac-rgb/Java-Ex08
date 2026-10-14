package ITSD.Entity;

import ITSD.Repository.IEntityIntPK;
import jakarta.persistence.*;

@Entity
public class OrderItem implements IEntityIntPK {

    @Id
    @Column(name="OrderItemID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "CustomerOrderID")
    CustomerOrder order;
    //many to one because one order can have many items
    @OneToOne
    @JoinColumn(name = "MenuItemID")
    MenuItem menuItem;

    int quantity;

    public OrderItem() {
    }

    public OrderItem(CustomerOrder order, MenuItem menuItem, int quantity) {
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public CustomerOrder getOrder() {
        return order;
    }
    public void setOrder(CustomerOrder order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
