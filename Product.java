package Inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Alec Holtzapfel
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * constructs product
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * sets id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * sets stock
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * sets min
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * sets max
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part part to add to associated parts list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * removes associated part
     * @param selectedAssociatedPart
     * @return true/false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }

    /**
     * @return associated parts list
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
