
//package tema2;
import java.util.*;

public class Entitate {
    
    private String name;
    private int no_attributes;
    private int rf;
    String primary_key;
    ArrayList<String> tip;
    ArrayList<String> value;

    public Entitate(String name,int rf ,int no_attributes,   ArrayList<String> value , ArrayList<String> tip) {
        this.no_attributes = no_attributes;
        this.name = name;
        this.rf = rf;
        this.tip = tip;
        this.value = value;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public int getNo_attributes() {
        return no_attributes;
    }

    public void setNo_attributes(int no_attributes) {
        this.no_attributes = no_attributes;
    }

    public int getRf() {
        return rf;
    }

    public void setRf(int rf) {
        this.rf = rf;
    }

    public ArrayList<String> getTip() {
        return tip;
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public String getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }
}
