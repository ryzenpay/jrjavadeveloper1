package me.ryzen.jrjava1;

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
    private String species;

    public void setFirstName(String name){
        if (name == null){
            logger.debug("null name provided in setFirstName");
            return;
        }
        this.firstName = name;
    }
    
    public void setLastName(String name) {
        if (name == null) {
            logger.debug("null name provided in setLastName");
            return;
        }
        this.lastName = name;
    }
    
    public void setSpecies(String species) {
        if (species == null) {
            logger.debug("null species provided in setSpecies");
            return;
        }
        this.species = species;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getSpecies(){
        return this.species;
    }
}
