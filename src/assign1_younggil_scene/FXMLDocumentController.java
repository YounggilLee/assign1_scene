/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.stage.Stage;

/**
 *
 * @author yglee
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField url;
    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Button button;
    @FXML
    private Stage stage;
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
    String address;
    String id;
    String pass;
    boolean result;
    JdbcHelper jdbc = new JdbcHelper();
    @FXML private TableView<Student> tableStudent;
    ArrayList<Student> students = new ArrayList<>();
    
    
//jdbc:mysql://localhost:3306/ejd
  @FXML
  private void handleButtonAction(ActionEvent event) throws SQLException {

//        this.address = url.getText();
//        this.id = user.getText();
//        this.pass = password.getText();
//        result =  jdbc.connect(url.getText(),user.getText(),password.getText());
       result = jdbc.connect("jdbc:mysql://localhost:3306/ejd", "root", "1234");    
       if(result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "DB is connected!! ");
            alert.showAndWait();
       }

    }
  @FXML
    private void handleRadioAction(ActionEvent event) {
        
        String query = null;
       
         String qry1 = "select id, firstname, lastName from student inner join CourseStudent \n"
                + "on student.id = CourseStudent.studentId where CourseStudent.courseId = 'PROG10000'";//'PROG10000'
        String qry2 = "select id, firstname, lastName from student inner join CourseStudent \n"
                + "on student.id = CourseStudent.studentId where CourseStudent.courseId = 'DBAS20000'";
        String qry3 = "select id, firstname, lastName from student inner join CourseStudent \n"
                + "on student.id = CourseStudent.studentId where CourseStudent.courseId = 'MATH30000'";

       
            try {
                if(program.isSelected()){
                    students.clear();
                   query = qry1;
                  
                    System.out.println("pro is selected");
                }
                if (dataBase.isSelected()){
                    students.clear();
                   query = qry2;
                  
                     System.out.println("database is selected");
                }
                if (math.isSelected()){
                    students.clear();
                    query = qry3;
                  
                     System.out.println("math is selected");
                }
              ResultSet rs = jdbc.query(query);
              
                while (rs.next()) {
                    String id = rs.getString(1);                    
                    String firstName = rs.getString(2);
                    String lastName = rs.getString(3);
                   students.add(new Student(id, firstName, lastName));                    
                  
                }
                  
                    tableStudent.setItems(FXCollections.observableArrayList(students));
                    columnId.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
                    columnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
                    columnLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
                    
            } catch (Exception e) {
                e.printStackTrace();
               
            }
        

    }
    
     @FXML
    private void handleRadioDataAction(ActionEvent event) {         
        handleRadioAction(event);        
    }
    
     @FXML
    private void handleRadioMathAction(ActionEvent event) {         
        handleRadioAction(event);        
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         ToggleGroup course = new ToggleGroup();
        program.setToggleGroup(course);
        dataBase.setToggleGroup(course);
        math.setToggleGroup(course); 
        
        button.setOnAction(event -> {
            try {
                handleButtonAction(event);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
//        p1.setOnAction(event -> {
//            handleRadioAction(event);
//        });

    }

}
