
//package tema2;

import java.util.*;
public class Instanta implements Cloneable{
    String entity_name;
    private ArrayList<Object> a_values;    //attribute values
    long timestamp;
    

    public Instanta(String entity_name, ArrayList<Object> a_values , long timestamp) {
        this.entity_name = entity_name;
        this.a_values = a_values;
        this.timestamp = timestamp;
    }
    
    
    
    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    
    public ArrayList<Object> getA_values() {
        return a_values;
    }
    @Override
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();  
    } 
}
