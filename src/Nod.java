
 
//package tema2;

import java.util.*;
public class Nod {
    private int index;   //astfel vom retine ordinea inainte de sortare , pastrand index ul initial
    ArrayList<Instanta> instances;

    public Nod(int index ,ArrayList<Instanta> instances) {
        this.index = index;
        this.instances = instances;
    }
    
    
    public ArrayList<Instanta> getInstances() {
        return instances;
    }

    public int getIndex() {
        return index;
    }
    
    
}

class SortByNodes implements Comparator<Nod>{
    
    @Override
    public int compare(Nod a , Nod b)
    {
        if( b.getInstances().size() == a.getInstances().size() )
            return a.getIndex() - b.getIndex();
        else
            return b.getInstances().size() - a.getInstances().size();
    }
}
