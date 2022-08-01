package Contollers;

import Inventory.Inventory;
import Inventory.Part;
import Inventory.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import Main.OtherMethods;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Alec Holtzapfel
 * @FUTUREIMPROVEMENT set the search field to change the results every time the
 *        text field changes
 */
public class MainMenuController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField partsSearchField;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInvColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TextField productsSearchField;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TableColumn<Product, Integer> productIDColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productIncColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    /**
     * This function searches the ID and Name Columns of the Part table
     *      for all matching parts. It searches when the user presses enter
     * @param event hitting the enter key
     */
    @FXML
    void onActionSearchFields(ActionEvent event) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        String searchFor = partsSearchField.getText();
        for (Part storedPart: Inventory.getAllParts()){
            String id = String.valueOf(storedPart.getId());
            if (id.contains(searchFor) || storedPart.getName().contains(searchFor)){
                filteredParts.add(storedPart);
            }
        }

        partsTableView.setItems(filteredParts);
    }

    /**
     * This function searches the ID and Name Columns of the product table
     *      for all matching product. It searches when the user presses enter
     * @param event hitting the enter key
     */
    @FXML
    void onActionSearchProductFields(ActionEvent event) {
        ObservableList<Product> filteredProduct = FXCollections.observableArrayList();

        String searchFor = productsSearchField.getText();
        for (Product storedProduct: Inventory.getAllProduct()){
            String id = String.valueOf(storedProduct.getId());
            if (id.contains(searchFor) || storedProduct.getName().contains(searchFor)){
                filteredProduct.add(storedProduct);
            }
        }

        productsTableView.setItems(filteredProduct);
    }

    /**
     * This function removes a part from the inventory list
     * @param event selecting the delete button
     * @RUNTIMEERROR This function will throw an error if no part is selected from the table;
     *          an alert window will appear if no part is selected
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        int selectedId = partsTableView.getSelectionModel().getSelectedIndex();
        if (selectedId == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "NO PART SELECTED; PLEASE SELECT A PART");

            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will permanently remove a part from the list.\n " +
                    "Do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.getAllParts().remove(selectedId);
            }
        }

    }

    /**
     * This function removes a product from the inventory list after checking for
     *      associated parts
     * @param event
     * @RUNTIMEERROR This function will throw an error if no product is selected from the table;
     *          an alert window will appear if no product is selected
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        int selectedId = productsTableView.getSelectionModel().getSelectedIndex();
        if (selectedId == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "NO PRODUCT SELECTED; PLEASE SELECT A PRODUCT");

            Optional<ButtonType> result = alert.showAndWait();
        }
        else if (Inventory.getAllProduct().get(selectedId).getAllAssociatedParts() !=null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"PRODUCT WITH ASSOCIATED PARTS CANNOT BE DELETED;\n" +
                    "PLEASE REMOVE ASSOCIATED PARTS");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will permanently remove a product from the list.\n " +
                    "Do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.getAllProduct().remove(selectedId);
            }
        }

    }

    /**
     * This function closes the main menu window and ends the session
     * @param event selecting the exit button
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * This function changes the stage to AddPartForm.fxml
     * @param event selecting the ADD button
     * @throws IOException
     */
    @FXML
    void onActionOpenAddPartMenu(ActionEvent event) throws IOException {
        OtherMethods.changeStage(event, stage, scene, "/views/AddPartForm.fxml");
    }

    /**
     * This function changes the stage to AddProductForm.fxml
     * @param event selecting the ADD button for product
     * @throws IOException
     */
    @FXML
    void onActionOpenAddProductMenu(ActionEvent event) throws IOException {
        OtherMethods.changeStage(event, stage, scene, "/views/AddProductForm.fxml");
    }

    /**
     *This function changes the stage to ModifyPartForm.fxml and populates the text fields
     * @param event selecting the Modify button
     * @throws IOException
     * @RUNTIMEERROR This function will throw an error if no part is selected from the table;
     *          an alert window will appear if no part is selected
     */
    @FXML
    void onActionOpenModifyPartMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ModifyPartForm.fxml"));
        loader.load();

        ModifyPartFormController ADMCtrl = loader.getController();
        if (partsTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"NO PART SELECTED;\n" +
                    "PLEASE SELECT A PART FROM THE TABLE");

            Optional<ButtonType> result = alert.showAndWait();
        }
        else{
            ADMCtrl.sendPart(partsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**
     *This function changes the stage to ModifyProductForm.fxml and populates the text fields
     * @param event selecting the Modify button
     * @throws IOException
     * @RUNTIMEERROR This function will throw an error if no product is selected from the table;
     *          an alert window will appear if no product is selected
     */
    @FXML
    void onActionOpenModifyProductsMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ModifyProductForm.fxml"));
        loader.load();

        ModifyProductFormController ADMCtrl = loader.getController();
        if (productsTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"NO PRODUCT SELECTED;\n" +
                    "PLEASE SELECT A PRODUCT FROM THE TABLE");

            Optional<ButtonType> result = alert.showAndWait();
        }
        else{
            ADMCtrl.sendAssocParts(productsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * Initializes MainMenu.fxml and populates the tables with inventory table
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        partsTableView.setItems(Inventory.getAllParts());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getAllProduct());

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productIncColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
