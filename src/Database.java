
//package tema2;

import java.util.*;

public class Database {
    
    String name;
    int no_nodes;
    int max_capacity;
    private ArrayList<Nod> nodes;
    private ArrayList<Entitate> entities;

    public Database(String name, int no_nodes,int max_capacity, ArrayList<Nod> nodes, ArrayList<Entitate> entities) {
        this.name = name;
        this.no_nodes = no_nodes;
        this.max_capacity = max_capacity;
        this.nodes = nodes;
        this.entities = entities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo_nodes() {
        return no_nodes;
    }

    public void setNo_nodes(int no_nodes) {
        this.no_nodes = no_nodes;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    

    public ArrayList<Nod> getNodes() {
        return nodes;
    }

    public ArrayList<Entitate> getEntities() {
        return entities;
    }
    
    
    
}
