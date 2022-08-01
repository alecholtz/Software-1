package Inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import Inventory.Part;
import Inventory.Product;

/**
 * @author Alec Holtzapfel
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProduct = FXCollections.observableArrayList();

    /**
     * adds new part to static inventory list
     * @param newPart
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * adds new product to static inventory list
     * @param newProduct
     */
    public static void addProduct(Product newProduct){
        allProduct.add(newProduct);
    }

    /**
     * searches for a partial or complete match to part id
     * @param partId
     * @return part
     */
    public static Part lookupPart(int partId){
        for (Part storedPart: Inventory.getAllParts()){
            if (storedPart.getId() == partId){
                return storedPart;
            }
        }

        return null;
    }

    /**
     * searches for a partial or complete match to product id
     * @param productId
     * @return product
     */
    public static Product lookupProduct(int productId){
        for (Product storedProduct: Inventory.getAllProduct()){
            if (storedProduct.getId() == productId){
                return storedProduct;
            }
        }

        return null;
    }

    /**
     * searches for a partial or complete match to part name
     * @param partName
     * @return part
     */
    public static Part lookupPart(String partName){
        for (Part storedParts: Inventory.getAllParts()){
            if(storedParts.getName() == partName){
                return storedParts;
            }
        }

        return null;
    }

    /**
     * searches for a partial or complete match to product name
     * @param productName
     * @return product
     */
    public static Product lookupProduct(String productName){
        for (Product storedProduct: Inventory.getAllProduct()){
            return storedProduct;
        }

        return null;
    }

    /**
     * deletes a part and saves new part information at the same index
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){
        Inventory.getAllParts().remove(index);
        Inventory.getAllParts().add(index, selectedPart);
    }

    /**
     * deletes a product and stores new part information at the same index
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct){
        Inventory.getAllProduct().remove(index);
        Inventory.getAllProduct().add(index, newProduct);
    }

    /**
     * removes a part from the inventory list
     * @param selectedPart
     * @return true/false
     */
    public static boolean deletePart(Part selectedPart){
        if(Inventory.getAllParts().remove(selectedPart)){
            return true;
        }

        return false;
    }

    /**
     * removes a product from the inventory list
     * @param selectedProduct
     * @return true/false
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (Inventory.getAllProduct().remove(selectedProduct)){
            return true;
        }
        return false;
    }

    /**
     *
     * @return all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return all products
     */
    public static ObservableList<Product> getAllProduct() {
        return allProduct;
    }
}
