package assign1_younggil_scene;

import javafx.beans.property.SimpleStringProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yglee
 */
public class Student {
    
    SimpleStringProperty id = null;
    SimpleStringProperty firstName = null;
    SimpleStringProperty lastName = null;
    
    public Student(String id, String firstName, String lastName) {
      this.id = new SimpleStringProperty(id);
    this.firstName = new SimpleStringProperty(firstName);
   this.lastName = new SimpleStringProperty(lastName);
    }

    public SimpleStringProperty getId() {
        return id;
    }

    public void setId(SimpleStringProperty id) {
        this.id = id;
    }

    public SimpleStringProperty getFirstName() {
        return firstName;
    }

    public void setFirstName(SimpleStringProperty firstName) {
        this.firstName = firstName;
    }

    public SimpleStringProperty getLastName() {
        return lastName;
    }

    public void setLastName(SimpleStringProperty lastName) {
        this.lastName = lastName;
    }

//    @Override
//    public String toString() {
//        return getId() + "\t" + getFirstName() + "\t" + getLastName() +"\t\n";
//    }  
    
    
}
