/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assign1_younggil_scene;

import java.net.URL;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private RadioButton p1;
    @FXML
    private RadioButton d2;
    @FXML
    private RadioButton m3;
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
    
    Connection conn;
    Statement stmt;
    ResultSet rs;

//jdbc:mysql://localhost:3306/ejd
  @FXML
  private void handleButtonAction(ActionEvent event) throws SQLException {

       // this.address = url.getText();
        //this.id = user.getText();
       // this.pass = password.getText();
        //result =  jdbc.connect(url.getText(),user.getText(),password.getText());
       // result = jdbc.connect("jdbc:mysql://localhost:3306/ejd", "root", "1234");
       
       try {
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejd", "root", "1234");
            this.stmt = conn.createStatement();
            System.out.println("connection ok");
           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
                     
        }
        System.out.println("connected!! ");
       

    }
  @FXML
    private void handleRadioAction(ActionEvent event) {
        
        System.out.println("p1 is selected!!!");
        String query1 = "select id, firstname, lastName from student inner join CourseStudent \n"
                + "on student.id = CourseStudent.studentId where CourseStudent.courseId = 'PROG10000'";

        
            try {

              //  ResultSet rs = jdbc.query(query1);
              ResultSet rs = stmt.executeQuery(query1);
                while (rs.next()) {
                    String id = rs.getString(1);                    
                    String firstName = rs.getString(2);
                    String lastName = rs.getString(3);
                    System.out.println(id+firstName+lastName);
                    Student std = new Student(id, firstName, lastName);
                    System.out.println(std.getId() + std.getFirstName());
                    students.add(std);
                    System.out.println(students.get(0));
                   // students.add(new Student(id, firstName, lastName));
                    
                    
                    tableStudent.setItems(FXCollections.observableArrayList(students));
                    columnId.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
                    columnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
                    columnLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
                }

            } catch (Exception e) {
                e.printStackTrace();
               
            }
        

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

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
