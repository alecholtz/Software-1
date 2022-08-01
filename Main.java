package Main;

import Inventory.Inventory;
import Inventory.Product;
import Inventory.InHouse;
import Inventory.Outsourced;
import Inventory.Part;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Alec Holtzapfel
 * Javadoc found at Alec_Holtzapel_C482_Practical_Exam\src\Main\JavaDoc
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * @author Alec Holtzapfel
     * @param args
     */

    public static void main(String[] args) {

        //Set the initial data
        Part part1 = new InHouse(1, "Capacitor", 14.99, 10,10,100, 103);
        Part part2 = new InHouse(2, "Inductor", 3.99, 100,10,100, 89);
        Part part3 = new InHouse(3, "Thermistor", 4.99, 2524,1000,10000, 73);
        Part part4 = new InHouse(4, "Choke", 25.99, 50,10,100, 14);
        Part part5 = new InHouse(5, "Output Contact", 14.99, 763,500,1000, 12);

        Part part6 = new Outsourced(6, "Solder", 0.99, 75,10,100, "GE");
        Part part7 = new Outsourced(7, "PCB", 299.99, 75,10,100, "GE");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);

        Inventory.addPart(part6);
        Inventory.addPart(part7);

        Product product1 = new Product(1, "I/O board", 1599.99, 25,1,25);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);
        product1.addAssociatedPart(part4);
        product1.addAssociatedPart(part5);

        Product product2 = new Product(2,"Board", 885.99, 100,1,200);
        product2.addAssociatedPart(part6);
        product2.addAssociatedPart(part7);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);


        launch(args);
    }
}
