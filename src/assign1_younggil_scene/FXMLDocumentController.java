/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assign1_younggil_scene;

import java.net.URL;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @FXML    private TextField url;
    @FXML    private TextField user;
    @FXML    private TextField password;
    @FXML    private Button button;
    @FXML    private Stage stage;
    @FXML    private RadioButton p1;
    @FXML    private RadioButton d2;
    @FXML    private RadioButton m3;
    @FXML    private TableColumn <Student, String> columnId;
    @FXML    private TableColumn <Student, String> columnFirstName;
    @FXML    private TableColumn <Student, String> columnLastName;
    String address;
    String id;
    String pass;
    boolean result;
    JdbcHelper jdbc = new JdbcHelper();
    TableView<Student> tableStudent;
//jdbc:mysql://localhost:3306/ejd
    private void handleButtonAction(ActionEvent event) throws SQLException {
        
        this.address = url.getText();
        this.id = user.getText();
        this.pass = password.getText();
        result =  jdbc.connect("jdbc:mysql://localhost:3306/ejd","root","1234");
        //result =  jdbc.connect(url.getText(),user.getText(),password.getText());  
        System.out.println("connected!! ");
        url.clear();
        user.clear();
        password.clear();

        
    }

    public void printResult(ResultSet resultSet) {
        
        try {
            while (resultSet.next()) {
                columnId.setId(resultSet.getString(1));
                columnFirstName.setId(resultSet.getString(2));
                columnLastName.setId(resultSet.getString(3));
                     tableStudent.getColumns().addAll(columnId,columnFirstName,columnLastName );
                        // tableStudent.add(new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
                    
            }
        } catch (SQLException ex) {
            Logger.getLogger(JdbcHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
     private void handleRadioAction(ActionEvent event){
          String query1 = "select id, firstname, lastName from student inner join CourseStudent \n" +
                            "on student.id = CourseStudent.studentId where CourseStudent.courseId = 'PROG10000'";
          String sql2 = "insert into Student ( id, firstName, lastName)values(?,?,?)";
                   
         
          if(event.getSource() == p1){ 
              System.out.println("p1 selected!!");
            //   ResultSet rs = jdbc.query(query1);
             //  printResult(rs); 
          }
          if(event.getSource() == d2){ 
              System.out.println("d2 selected!!");
              // ResultSet rs = jdbc.query(query1);
             //  printResult(rs); 
          }
          if(event.getSource() == m3){ 
              System.out.println("m3 selected!!");
              // ResultSet rs = jdbc.query(query1);
              // printResult(rs); 
          }
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        columnId.setCellValueFactory(new PropertyValueFactory<Student, String> ("id"));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String> ("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<Student, String> ("lastName"));

        button.setOnAction(event -> {
            try {
                handleButtonAction(event);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
        
        p1.setOnAction(event -> {        
                handleRadioAction(event);           
        });
        
        d2.setOnAction(event -> {        
                handleRadioAction(event);           
        });
        
        m3.setOnAction(event -> {        
                handleRadioAction(event);           
        });
        
    }

}
