package assign1_younggil_scene;

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
    
    String id = null;
    String firstName = null;
    String lastName = null;
    
    public Student(String sId, String sFirstName, String sLastName) {
      this.id = sId;
    this.firstName = sFirstName;
   this.lastName = sLastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    @Override
//    public String toString() {
//        return getId() + "\t" + getFirstName() + "\t" + getLastName() +"\t\n";
//    }  
    
    
}
