package Contollers;

import Inventory.Inventory;
import Inventory.Part;
import Inventory.Product;
import Main.OtherMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Alec Holtzapfel
 * @FUTUREIMPROVEMENT check for an existing id of the same value
 *      and require unique ids
 */
public class AddProductFormController implements Initializable {
    Stage stage;
    Parent scene;
    ObservableList<Part> assocParts = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> allPartsTableView;

    @FXML
    private TableColumn<Part, Integer> allPartsId;

    @FXML
    private TableColumn<Part, String> allPartsName;

    @FXML
    private TableColumn<Part, Integer> allPartsInv;

    @FXML
    private TableColumn<Part, Double> allPartsPrice;

    @FXML
    private TableView<Part> assocPartsTableView;

    @FXML
    private TableColumn<Part, Integer> assocPartsId;

    @FXML
    private TableColumn<Part, String> assocPartsName;

    @FXML
    private TableColumn<Part, Integer> assocPartsInv;

    @FXML
    private TableColumn<Part, Double> assocPartsPrice;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField max;

    @FXML
    private TextField min;

    @FXML
    private TextField inv;

    @FXML
    private TextField price;

    @FXML
    private TextField searchTextField;

    /**
     * This function adds the selected part to a temporary list of associated parts.
     *      NOTE: the part will not be added to the inventory list associated with a product
     *          until the save button is selected
     *
     * @param event selecting the add button
     * @throws IOException
     * @RUNTIMEERROR This function will throw an error if no part is selected from the table;
     *          an alert window will appear if no part is selected
     */
    @FXML
    void onActionAdd(ActionEvent event) throws IOException {
        int selectedId = allPartsTableView.getSelectionModel().getSelectedIndex();

        if (selectedId == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "NO PART SELECTED; PLEASE SELECT A PART");

            Optional<ButtonType> result = alert.showAndWait();
        }
        else{
            assocParts.add(Inventory.getAllParts().get(selectedId));
            assocPartsTableView.setItems(assocParts);

            assocPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
            assocPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
            assocPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
            assocPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /**
     * Changes the stage to MainMenu.fxml without adding a product to inventory
     * @param event selecting the cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        OtherMethods.changeStage(event, stage, scene, "/Views/MainMenu.fxml");
    }

    /**
     * Removes associated part from the temporary associated parts list
     * @param event selecting remove associated part button
     * @RUNTIMEERROR This function will throw an error if no part is selected from the table;
     *          an alert window will appear if no part is selected
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        int selectedId = assocPartsTableView.getSelectionModel().getSelectedIndex();

        if (selectedId == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "NO PART SELECTED; PLEASE SELECT A PART");

            Optional<ButtonType> result = alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the part from the associated parts list.\n" +
                    "Do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove(selectedId);
                assocPartsTableView.setItems(assocParts);

                assocPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
                assocPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
                assocPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
                assocPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            }

        }
    }

    /**
     * Saves new product to inventory using the text input and temporary parts list
     *      note: an error window will open if input values are not the correct type or
     *          min>max
     * @param event selecting the save button
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        String NAME = name.getText();

        try {
            int newId = Inventory.getAllProduct().size() + 1;
            int INV = Integer.parseInt(inv.getText());
            Double PRICE = Double.parseDouble(price.getText());
            int MIN = Integer.parseInt(min.getText());
            int MAX = Integer.parseInt(max.getText());

            if(MIN>MAX || INV>MAX || INV<MIN){
                Alert alert = new Alert(Alert.AlertType.WARNING,"MIN/MAX VALUE ERROR;\n" +
                        "PLEASE ENSURE MIN < Inventory < MAX");

                Optional<ButtonType> result = alert.showAndWait();
            }
            else{
                Product newProduct = new Product(newId, NAME, PRICE, INV, MIN, MAX);
                for (Part partInList : assocParts) {
                    newProduct.addAssociatedPart(partInList);
                }

                Inventory.addProduct(newProduct);

                OtherMethods.changeStage(event, stage, scene, "/Views/MainMenu.fxml");
            }
        }
        catch (NumberFormatException err){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error " + err.getLocalizedMessage() +"\nPLEASE ENTER AN INTEGER OR FLOAT");

            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * This function searches the ID and Name Columns of the Part table
     *      for all matching parts. It searches when the user presses enter
     * @param event hitting the enter key
     */
    @FXML
    void onActionSearchParts(ActionEvent event) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        String searchFor = searchTextField.getText();
        for (Part storedPart: Inventory.getAllParts()){
            String id = String.valueOf(storedPart.getId());
            if (id.contains(searchFor) || storedPart.getName().contains(searchFor)){
                filteredParts.add(storedPart);
            }
        }

        allPartsTableView.setItems(filteredParts);
    }

    /**
     * intitializes AddProductForm.fxml and populates the tables
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        allPartsTableView.setItems(Inventory.getAllParts());

        allPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
