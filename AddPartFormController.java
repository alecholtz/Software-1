package Contollers;

import Inventory.InHouse;
import Inventory.Outsourced;
import Inventory.Inventory;
import Inventory.Part;
import Main.OtherMethods;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Alec Holtzapfel
 * @FUTUREIMPROVEMENT check for an existing id of the same value
 *      and require unique ids
 */
public class AddPartFormController {

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
    void onActionCancel(ActionEvent event) throws IOException{
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
            int newId = Inventory.getAllParts().size() + 1;
            int INV = Integer.parseInt(inv.getText());
            Double PRICE = Double.parseDouble(price.getText());
            int MIN = Integer.parseInt(min.getText());
            int MAX = Integer.parseInt(max.getText());

            if (MIN > MAX || INV>MAX || INV<MIN){
                Alert alert = new Alert(Alert.AlertType.WARNING,"MIN/MAX VALUE ERROR;\n" +
                        "PLEASE ENSURE MIN < Inventory < MAX");

                Optional<ButtonType> result = alert.showAndWait();
            }
            else {
                if (INHOUSE.isSelected()) {
                    int machineId = Integer.parseInt(toggledTextOption.getText());

                    Part newPart = new InHouse(newId, NAME, PRICE, INV, MIN, MAX, machineId);
                    Inventory.addPart(newPart);
                } else if (OUTSOURCED.isSelected()) {
                    String company = toggledTextOption.getText();

                    Part newPart = new Outsourced(newId, NAME, PRICE, INV, MIN, MAX, company);
                    Inventory.addPart(newPart);
                }
                OtherMethods.changeStage(event, stage, scene, "/views/MainMenu.fxml");
            }
        }
        catch (NumberFormatException err){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error " + err.getLocalizedMessage() +"\nPLEASE ENTER AN INTEGER OR FLOAT");

            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * changes the text fields based on which radio button is selected
     * @param actionEvent changing the toggled option
     */
    @FXML
    public void onActionSwapToggleText(ActionEvent actionEvent) {
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
}
