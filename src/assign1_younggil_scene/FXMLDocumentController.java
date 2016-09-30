package assign1_younggil_scene;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import ejd.JdbcHelper;
import javafx.application.Platform;
import javafx.stage.Stage;

/** 
 * Author:  Younggil Lee
 * Student ID: 991 395 505
 * Description: Control all GUI(get user data and display it on table view)  
 **/

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField turl;
    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Button button;
    @FXML
    private RadioButton program;
    @FXML
    private RadioButton dataBase;
    @FXML
    private RadioButton math;
    @FXML
    private TableColumn<Student, String> columnId;
    @FXML
    private TableColumn<Student, String> columnFirstName;
    @FXML
    private TableColumn<Student, String> columnLastName;
    @FXML
    private TableView<Student> tableStudent;

    // instance variables
    private String address;
    private String id;
    private String pass;
    private boolean result;
    JdbcHelper jdbc = new JdbcHelper();
    ArrayList<Student> students = new ArrayList<>();
    private Stage stage;

    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        
        // check button condition       
        if (button.getText() == "Disconnect") {
            
            // if button is disconnect, clear all textfields
            turl.setText(null);
            user.setText(null);
            password.setText(null);
            
             // disconnect jdbc connection
            if (jdbc != null) {
                jdbc.disconnect();
             }
            
            // change button condition to connect
            button.setText("Connect");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "DataBase is disconnected!! ");
            alert.showAndWait();
            
          // if button condition is connect  
        } else {
            //if textfields is null, put log-in data
            if (turl.getText() == null) {
                turl.setText("jdbc:mysql://localhost:3306/ejd");
            }
            if (user.getText() == null) {
                user.setText("root");
            }
            if (password.getText() == null) {
                password.setText("1234");
            }
            
            //get the user's input from textfeild 
            this.address = turl.getText();
            this.id = user.getText();
            this.pass = password.getText();
            
            // store result of connection to boolean value
            result = jdbc.connect(address, id, pass);
            
            //show result to window
            if (result) {
                button.setText("Disconnect");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "DataBase is connected!! ");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please check log-in information!! ");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleRadioAction(ActionEvent event) {
        // local value for store the user's input
        int radioNumber = 0;
        
        // query to get data from database
        String query = "select id, firstname, lastName from student inner join CourseStudent \n"
                + "on student.id = CourseStudent.studentId where CourseStudent.courseId = ?";

        // check the user input
        // if database is not connected, display alert to the user
        try {
           
            if (program.isSelected()) {  
                if(checkConection()){
                students.clear();
                radioNumber = 1;
                }else{
                      Alert alert = new Alert(Alert.AlertType.INFORMATION, "Database connection first!! ");
                alert.showAndWait();
            }
            }
            if (dataBase.isSelected()) {
                 if(checkConection()){
                students.clear();
                radioNumber = 2;
                 }else{
                      Alert alert = new Alert(Alert.AlertType.INFORMATION, "Database connection first!! ");
                alert.showAndWait();
                }
            }
            if (math.isSelected()) {
                if(checkConection()){
                students.clear();
                radioNumber = 3;
                 }else{
                      Alert alert = new Alert(Alert.AlertType.INFORMATION, "Database connection first!! ");
                alert.showAndWait();
                }
            }
           
            // store rssult of  the query
            ResultSet rs = jdbc.query(radioNumber, query);
            
            //put data to stduents arraylist
            while (rs.next()) {
                String id = rs.getString(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                students.add(new Student(id, firstName, lastName));

            }
             // diplay ths data on console 
            System.out.println("========================================");
            System.out.println("   ID        First Name    Last Name    ");
            System.out.println("========================================");
            for (Student std : students) {                
                System.out.printf("%5s %10s %10s\n", std.getId(), std.getFirstName(), std.getLastName());                
            }
            System.out.println("=========================================");
            
            // display the data on table view
            tableStudent.setItems(FXCollections.observableArrayList(students));
            columnId.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
            columnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
            columnLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    // this method for closing window
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "DataBase will be disconnected!! ");
            alert.showAndWait();
            if (jdbc != null) {
                jdbc.disconnect();
            }
            Platform.exit();
        });
    }
 
// check current statement of jdbc connection   
 public boolean checkConection() throws SQLException {
  
     boolean check = jdbc.getConnection().isValid(0);
     
     return check;
 }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set textfields 
        turl.setText("jdbc:mysql://localhost:3306/ejd");
        user.setText("root");
        password.setText("1234");
        
        // create togglegroup
        ToggleGroup course = new ToggleGroup();
        program.setToggleGroup(course);
        dataBase.setToggleGroup(course);
        math.setToggleGroup(course);
        
        // send button event to event handler
        button.setOnAction(event -> {
            try {
                handleButtonAction(event);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });

    }

}
