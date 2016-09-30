/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assign1_younggil_scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** 
 * Author:  Younggil Lee
 * Student ID: 991 395 505
 * Description: Main class for GUI 
 **/
public class Assign1_Younggil_Scene extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        //send the stage to contorller
        FXMLDocumentController ctrl = loader.getController();
        ctrl.setStage(stage);

        Scene scene = new Scene(root, 650, 400);
        stage.setTitle("Assignment1");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
