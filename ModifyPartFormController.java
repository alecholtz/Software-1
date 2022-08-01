package Contollers;

import Inventory.InHouse;
import Inventory.Inventory;
import Inventory.Outsourced;
import Inventory.Part;
import Main.OtherMethods;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Alec Holtzapfel
 */
public class ModifyPartFormController {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton INHOUSE;
    
    @FXML
    private RadioButton OUTSOURCED;

    @FXML
    private Text inHouseToggleOption;

    @FXML
    private Text outsourcedToggleOption;


    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField inv;

    @FXML
    private TextField price;

    @FXML
    private TextField min;

    @FXML
    private TextField max;

    @FXML
    private TextField toggledTextOption;

    /**
     * Changes the stage to MainMenu.fxml without saving part to inventory
     * @param event selecting the cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        OtherMethods.changeStage(event, stage, scene, "/views/MainMenu.fxml");
    }

    /**
     * saves part to inventory
     *      note: will open an alert window if data of the incorrect type is entered
     * @param event selecting the save button
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException{
        String NAME = name.getText();

        try {
            int ID = Integer.parseInt(id.getText());
            int INV = Integer.parseInt(inv.getText());
            Double PRICE = Double.parseDouble(price.getText());
            int MIN = Integer.parseInt(min.getText());
            int MAX = Integer.parseInt(max.getText());

            int index = 0;
            for (Part selectedPart : Inventory.getAllParts()) {
                if (ID == selectedPart.getId()) {
                    break;
                }
                index++;
            }

            if (MIN>MAX || INV>MAX || INV<MIN){
                Alert alert = new Alert(Alert.AlertType.WARNING,"MIN/MAX VALUE ERROR;\n" +
                        "PLEASE ENSURE MIN < Inventory < MAX");

                Optional<ButtonType> result = alert.showAndWait();
            }
            else {
                if (INHOUSE.isSelected()) {
                    int machineId = Integer.parseInt(toggledTextOption.getText());
                    Inventory.updatePart(index, new InHouse(ID, NAME, PRICE, INV, MIN, MAX, machineId));
                } else if (OUTSOURCED.isSelected()) {
                    String companyName = toggledTextOption.getText();
                    Inventory.updatePart(index, new Outsourced(ID, NAME, PRICE, INV, MIN, MAX, companyName));
                }

                OtherMethods.changeStage(event, stage, scene, "/views/MainMenu.fxml");
            }
        }
        catch (NumberFormatException err) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error " + err.getLocalizedMessage() +"\nPLEASE ENTER AN INTEGER OR FLOAT");

            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * changes the text fields based on which radio button is selected
     * @param event changing the toggled option
     */
    @FXML
    void onActionHideText(ActionEvent event) {
        if (INHOUSE.isSelected()){
            inHouseToggleOption.setVisible(true);
            outsourcedToggleOption.setVisible(false);
        }
        else if (OUTSOURCED.isSelected()){
            inHouseToggleOption.setVisible(false);
            outsourcedToggleOption.setVisible(true);
        }

        return;
    }

    /**
     * sends part information from MainMenu to ModifyPartForm
     * @param selectedPart part selected in the main menu table
     */
    public void sendPart(Part selectedPart){
        id.setText(String.valueOf(selectedPart.getId()));
        name.setText(selectedPart.getName());
        inv.setText(String.valueOf(selectedPart.getStock()));
        price.setText(String.valueOf(selectedPart.getPrice()));
        min.setText(String.valueOf(selectedPart.getMin()));
        max.setText(String.valueOf(selectedPart.getMax()));

        if (selectedPart instanceof InHouse){
            INHOUSE.setSelected(true);
            OUTSOURCED.setSelected(false);

            inHouseToggleOption.setVisible(true);
            outsourcedToggleOption.setVisible(false);

            toggledTextOption.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        else if (selectedPart instanceof Outsourced){
            INHOUSE.setSelected(false);
            OUTSOURCED.setSelected(true);

            inHouseToggleOption.setVisible(false);
            outsourcedToggleOption.setVisible(true);

            toggledTextOption.setText(((Outsourced) selectedPart).getCompanyName());
        }
    }
}
