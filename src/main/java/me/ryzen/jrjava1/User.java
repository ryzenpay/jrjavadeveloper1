package me.ryzen.jrjava1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name="users")
public class User {
    private static final Logger logger = LogManager.getLogger("User");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private int age;
    private String species;
    private boolean dead = false;

    public User(){

    }
    public User(String firstName, String lastName, int age, String species, boolean dead){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.species = species;
        this.dead = dead;
    }

    public void setFirstName(String name){
        if (name == null){
            logger.debug(this.toString()+": null name provided in setFirstName");
            return;
        }
        this.firstName = name;
    }

    public String getFirstName() {
        return this.firstName;
    }
    
    public void setLastName(String name) {
        if (name == null) {
            logger.debug(this.toString()+ ": null name provided in setLastName");
            return;
        }
        this.lastName = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setAge(int age){
        if (age < 0){
            logger.debug(this.toString() + ": age less than 0 provided in setAge");
        }
        this.age = age;
    }

    public int getAge(){
        return this.age;
    }
    
    public void setSpecies(String species) {
        if (species == null) {
            logger.debug(this.toString() +": null species provided in setSpecies");
            return;
        }
        this.species = species;
    }

    public String getSpecies(){
        return this.species;
    }

    public void setDead(boolean isDead){
        this.dead = isDead;
    }

    public boolean isDead(){
        return this.dead;
    }

    public static ArrayList<String> getColumnNames(){
        // TODO: make adaptive work
        // also needs to be used in getRow then
        // ArrayList<String> fieldNames  = new ArrayList<>();
        // for (Field field : User.class.getFields()){
        //     fieldNames.add(field.getName());
        // }
        // return fieldNames;
        return new ArrayList<>(Arrays.asList(new String[]{"First Name", "Last Name", "Age", "Species", "Deceased"}));
    }

    @Override
    public String toString(){
        return firstName + lastName;
    }

    public ArrayList<Object> getRow(){
        return new ArrayList<>(Arrays.asList(new Object[]{this.firstName, this.lastName, this.age, this.species, this.dead}));
    }

    public Object getRowValue(int i){
        return this.getRow().get(i);
    }
}
