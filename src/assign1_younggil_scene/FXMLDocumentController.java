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
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
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
    private String address;
    private String id;
    private String pass;
    private boolean result;
    JdbcHelper jdbc = new JdbcHelper();
    ArrayList<Student> students = new ArrayList<>();
    private Stage stage;

    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        
            this.address = url.getText();
            this.id = user.getText();
            this.pass = password.getText();
            
            result = jdbc.connect(address, id, pass);
            if (result) {               
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "DataBase is connected!! ");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please check log-in information!! ");
                alert.showAndWait();
            }
        


    }

    @FXML
    private void handleRadioAction(ActionEvent event) {
        int radioNumber = 0;

        String query = "select id, firstname, lastName from student inner join CourseStudent \n"
                + "on student.id = CourseStudent.studentId where CourseStudent.courseId = ?";

        try {
            if (program.isSelected()) {
                students.clear();
                radioNumber = 1;
            }
            if (dataBase.isSelected()) {
                students.clear();
                radioNumber = 2;
            }
            if (math.isSelected()) {
                students.clear();
                radioNumber = 3;
            }
            ResultSet rs = jdbc.query(radioNumber, query);

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

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(e -> {
             
           
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "DataBase will be disconnected!! ");
                alert.showAndWait();
                 if (jdbc != null) jdbc.disconnect(); 
               Platform.exit();                       
                        
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to quit?");
//            Optional<ButtonType> result = alert.showAndWait(); 
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                                
//                if (jdbc != null) jdbc.disconnect();
//                
//                Platform.exit();
//            } else 
//                // do nothing 
//                e.consume(); // ignore the close event  
        });
        
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

    }

}
