package Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

/** @Author Alec Holtzapfel
 *
 */
public class OtherMethods {
    /**OtherMethods contains static methods which remove the use of redundant code
     */

    static public void changeStage(ActionEvent event, Stage stage, Parent scene, String stageName) throws IOException{

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(OtherMethods.class.getResource(stageName));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * @param event button press to change stage
     * @param stage new stage
     * @param scene new scene
     * @param stageName the name of the .fxml document for the new window
     * @throws IOException
    */
}
