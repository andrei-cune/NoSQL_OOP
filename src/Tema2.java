
//package tema2;

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.lang.*;

public class Tema2 {

 
    public static void main(String[] args) {
      
        try
        {
            File file_in = new File(args[0]);
            File file_out = new File( args[0] + "_out" );
            
            Scanner input = new Scanner(file_in);
            PrintWriter output = new PrintWriter(file_out);
            
            DecimalFormat f = new DecimalFormat("#.##");
            
            ArrayList<Nod> nodes = new ArrayList<>();
            ArrayList<Entitate> entities = new ArrayList<>();  //initializare baza de date goala
            Database db = new Database("NULL",0,0,nodes,entities );
            
            while(input.hasNextLine() == true )
            {   
                
                String cmd = input.nextLine();
                StringTokenizer string = new StringTokenizer( cmd );
                if( string.hasMoreTokens() )
                    cmd = string.nextToken();    //comanda curenta
                    
                if( cmd.equals("CREATEDB") == true )
                {   
                       //initializare baza de date
                    
                    if( string.hasMoreTokens() )
                        db.setName(string.nextToken());
                    
                    if( string.hasMoreTokens() )
                        db.setNo_nodes(Integer.parseInt(string.nextToken())); //setam atributele
                    
                    if( string.hasMoreTokens() )
                        db.setMax_capacity(Integer.parseInt(string.nextToken()));
                    
                    for( int i = 0 ; i < db.getNo_nodes() ; i ++ )
                    {    
                        ArrayList<Instanta> Instances = new ArrayList<>();  //Formam nodurile goale ale bazei de date
                        Nod n = new Nod(i,Instances);
                        db.getNodes().add(n);   //addaugam nodurile
                    }
                }
                else if (cmd.equals("CREATE") == true )
                {   
                    
                    ArrayList<String> tip = new ArrayList<>();
                    ArrayList<String> value = new ArrayList<>();
                    Entitate e = new Entitate("NULL",0,0,value,tip);  //initializare entitate goala
                    
                    if( string.hasMoreTokens() )
                        e.setName(string.nextToken());
                    if( string.hasMoreTokens() )
                        e.setRf(Integer.parseInt(string.nextToken()));   //setam caracteristicile entitatii
                    if( string.hasMoreTokens() )
                        e.setNo_attributes(Integer.parseInt(string.nextToken()));
                    
                    int i;
                    for( i=0 ; i < e.getNo_attributes() ; i++ )
                    {
                        e.value.add(string.nextToken());  //atributul
                        e.tip.add(string.nextToken());  //tipul atributului
                        
                    }
                    
                    e.setPrimary_key(e.value.get(0));                  //Setam cheia primara primul atribut
                    db.getEntities().add(e);  //adaugare
                    
                    
                    
                }
                else if( cmd.equals( "INSERT" ))
                {
                    ArrayList<Object> values = new ArrayList<>();
                    Instanta i = new Instanta("NULL",values,System.nanoTime() ); //initializare
                    
                    if( string.hasMoreTokens() )
                        i.setEntity_name(string.nextToken());
                    
                    
                    
                    Entitate e = db.getEntities().get(0);    //vom initializa entiatea pe care o cautam cu prima din sir,
                    for(Entitate j : db.getEntities() )       // aceasta urmand sa fie setata oricum ulterior
                    
                   
                        if (j.getName().equals(i.getEntity_name() ))   //gasim entiatea din care face parte instanta
                            e = j;
                    
                    for( int idx = 0; idx < e.getNo_attributes() ; idx ++ )
                    {
                        if( e.getTip().get( idx ).equals("Integer"))
                            i.getA_values().add(Integer.parseInt(string.nextToken() )); //verificam de ce tip este atributul
                        
                        if( e.getTip().get( idx ).equals("Float"))                       // vom adauga valorile atributelor in instanta
                            i.getA_values().add( f.format(Float.parseFloat(string.nextToken()) ));
                        
                        if( e.getTip().get( idx ).equals("String"))
                            i.getA_values().add((string.nextToken() ));  
                    }
                    SortByNodes criteriu =new SortByNodes();   //soram dupa numarul de instante
                    Collections.sort(db.getNodes(), criteriu );
                    int rf1 = e.getRf();
                    try
                    {   
                        for(int k=0; k < db.getNo_nodes(); k++)
                        {
                            Instanta s = (Instanta)i.clone();
                            while(rf1 > 0 && db.getNodes().get(k).getInstances().size() < db.getMax_capacity())
                            {
                                db.getNodes().get(k).getInstances().add(0,s);        //inseram rf instante in rf noduri
                                rf1--;
                                break;
                            }
                        }
                                
                        
                    }
                    catch(CloneNotSupportedException ex)
                    {
                        System.out.println("Clone not supported!");
                    }
                }
                
                else if (cmd.equals("DELETE"))
                {
                    boolean found = false;
                    String entityName = "NULL";
                    String primaryKey = "NULL";
                    if( string.hasMoreTokens() )
                        entityName = string.nextToken();
                    
                    if( string.hasMoreTokens() )
                        primaryKey = string.nextToken();
                    
                    Entitate e = db.getEntities().get(0);    //Initializare
                    for(Entitate j : db.getEntities() )       
                    
                        if (j.getName().equals(entityName))     //gasim entitatea respectiva
                            e = j;
                    
                    for( Nod n : db.getNodes() )
                    {
                        for( int i = 0 ; i < n.getInstances().size() ; i++ )
                        {
                            if(n.getInstances().get(i).getEntity_name().equals(entityName) )
                            {   
                                String parsed_pk = "NULL";    //parsed primary key , vom memora 
                                
                                if( e.getTip().get(0).equals("Integer") )   //verificam tipul cheii primare 
                                    parsed_pk = Integer.toString( (Integer) n.getInstances().get(i).getA_values().get(0) );
                                
                                if( e.getTip().get(0).equals("Float") )   // trecem in string valoarea cheii primare  
                                    parsed_pk = f.format(Float.toString( (Float) n.getInstances().get(i).getA_values().get(0)) );
                                
                                if( e.getTip().get(0).equals("String") )
                                    parsed_pk = (String) n.getInstances().get(i).getA_values().get(0);
                                
                                if( parsed_pk.equals( primaryKey ))  //verificam daca in instanta curenta avem de a face cu aceeasi cheie primara 
                                {
                                    n.getInstances().remove(i);   //eliminare
                                    found = true;
                                }
                                
                            }
                        }        
                    }
                    if( found == false )
                        output.println("NO INSTANCE TO DELETE");
                }
                else if( cmd.equals("GET"))
                {   
                    boolean found = false;
                    String entityName = "NULL";
                    String primaryKey = "NULL";
                    if( string.hasMoreTokens() )
                        entityName = string.nextToken();
                    
                    if( string.hasMoreTokens() )
                        primaryKey = string.nextToken();
                    
                    Entitate e = db.getEntities().get(0);    //Initializare
                    for(Entitate j : db.getEntities() )       
                    
                        if (j.getName().equals(entityName))   
                            e = j;
                    
                    Instanta inst = db.getNodes().get(0).getInstances().get(0);//initializare
                    for( Nod n : db.getNodes() )
                    {   
                        
                        for( int i = 0 ; i < n.getInstances().size() ; i++ )
                        {   
                            if(n.getInstances().get(i).getEntity_name().equals(entityName) )
                            {   
                               
                                
                                String parsed_pk = "NULL";    //parsed primary key ,acelasi principiu
                                
                                if( e.getTip().get(0).equals("Integer") )          ///acelasi procedeu///
                                    parsed_pk = Integer.toString( (Integer) n.getInstances().get(i).getA_values().get(0) );
                                
                                if( e.getTip().get(0).equals("Float") )
                                    parsed_pk = f.format(Float.toString( (Float) n.getInstances().get(i).getA_values().get(0)) );
                                
                                if( e.getTip().get(0).equals("String") )
                                    parsed_pk = (String) n.getInstances().get(i).getA_values().get(0);
                                
                                if( parsed_pk.equals( primaryKey ))
                                {   
                                    inst = n.getInstances().get(i);
                                    
                                    found = true;
                                    break;
                                }
                                
                                
                            }
                        }        
                    }
                    if( found == true )   //facem afisarea doar daca found e true
                    {   
                        for( Nod n : db.getNodes() )
                        {
                           
                             for( int i = 0 ; i < n.getInstances().size() ; i++ )
                             {
                               if(n.getInstances().get(i).getEntity_name().equals(entityName) )
                               {   
                                       // nodurile ce vor fi afisate sunt gasite prin acelasi procedeu , dupa entitate so cheie primra
                                
                                    String parsed_pk = "NULL";    //parsed primary key , vom memora 
                                
                                    if( e.getTip().get(0).equals("Integer") )
                                        parsed_pk = Integer.toString( (Integer) n.getInstances().get(i).getA_values().get(0) );
                                
                                    if( e.getTip().get(0).equals("Float") )
                                        parsed_pk = f.format(Float.toString( (Float) n.getInstances().get(i).getA_values().get(0)) );
                                
                                    if( e.getTip().get(0).equals("String") )
                                        parsed_pk = (String) n.getInstances().get(i).getA_values().get(0);
                                
                                    if( parsed_pk.equals( primaryKey ))
                                    {   
                                        output.print("Nod" + (n.getIndex()+1) + " ");
                                    }
                               }
                             }
                        }
                        
                        
                        output.print(entityName + " ");
                        for( int k = 0 ; k < e.getNo_attributes()-1 ; k ++ )  //afisarea atributelor
                            output.print(e.getValue().get(k) + ":" + 
                            inst.getA_values().get(k) +" ");
                            
                                         //Urmeaza sa printam si ultimul atribut si tipul sau
                           
                            output.println(e.getValue().get(e.getNo_attributes()-1) + ":" + 
                            inst.getA_values().get(e.getNo_attributes()-1 ));
                                
                    }
                    else
                        output.println("NO INSTANCE FOUND");
                }
                else if( cmd.equals("UPDATE"))
                {   
                   
                    
                     String entityName = "NULL";
                    String primaryKey = "NULL";
                    if( string.hasMoreTokens() )
                        entityName = string.nextToken();
                    
                    if( string.hasMoreTokens() )
                        primaryKey = string.nextToken();
                    
                    Entitate e = db.getEntities().get(0);    //Initializare
                    for(Entitate j : db.getEntities() )       
                    
                        if (j.getName().equals(entityName))   
                            e = j;
                     Instanta inst = db.getNodes().get(0).getInstances().get(0);//initializare
                    
                    while( string.hasMoreTokens() )
                    {   
                        String attr = string.nextToken();
                        String new_attr_value = string.nextToken();
                        for( Nod n : db.getNodes() )
                        {
                            for( int i = 0 ; i < n.getInstances().size() ; i++ )
                            {
                                if(n.getInstances().get(i).getEntity_name().equals(entityName) )
                                {   
                              
                                    String parsed_pk = "NULL";    //parsed primary key ,procedeu identic
                                
                                    if( e.getTip().get(0).equals("Integer") )
                                        parsed_pk = Integer.toString( (Integer) n.getInstances().get(i).getA_values().get(0) );
                                
                                    if( e.getTip().get(0).equals("Float") )
                                        parsed_pk = f.format(Float.toString( (Float) n.getInstances().get(i).getA_values().get(0)) );
                                
                                    if( e.getTip().get(0).equals("String") )
                                        parsed_pk = (String) n.getInstances().get(i).getA_values().get(0);
                                
                                    if( parsed_pk.equals( primaryKey ))
                                    {
                                        inst = n.getInstances().get(i);
                                        
                                        //am gasit instanta
                                        //aplicam acelasi principiu si pentru valorile atributelor
                                        for(int k = 0; k < inst.getA_values().size() ; k ++)
                                        {   
                                            boolean is_int = false,is_float = false,is_string = false;
                                            String parsed_attr = "NULL";
                                            if(e.getTip().get(k).equals("Integer"))
                                            {
                                                parsed_attr = e.getValue().get(k) ;
                                                is_int = true;
                                            }
                                            
                                            if(e.getTip().get(k).equals("Float"))
                                            {
                                                parsed_attr = e.getValue().get(k);
                                                is_float = true;
                                            }
                                            
                                            if(e.getTip().get(k).equals("String"))
                                            {
                                                parsed_attr = e.getValue().get(k);
                                                is_string = true;
                                            }
                                            
                                            if( parsed_attr.equals(attr) )   //am gasit atributul de inlocuit
                                            {
                                                if( is_int == true )
                                                    inst.getA_values().set(k, (Integer)(Integer.parseInt((new_attr_value))));
                                                if( is_float == true )
                                                    inst.getA_values().set(k, f.format((Float)Float.parseFloat((new_attr_value))));
                                                if( is_string == true )
                                                    inst.getA_values().set(k, new_attr_value);  //inlocuim atributul in funcie de tip
                                            }
                                            
                                        }
                                       try
                                       {
                                           Instanta s = (Instanta)inst.clone();
                                           n.getInstances().remove(inst);    //punera noii instante in locul corespunzator
                                           n.getInstances().add(0,s);
                                       }
                                       catch(CloneNotSupportedException ex)
                                        {
                                            System.out.println("Clone not supported!");
                                        }
                                    }
                                }
                            }
                        }
                    }  
                    
                }
                else if( cmd.equals("SNAPSHOTDB") )
                {   
                    boolean instance_found = false;
                    for( int i = 0 ; i < db.getNodes().size() ; i++ )
                    {   
                        if( db.getNodes().get(i).getInstances().size()> 0)
                        output.println("Nod" + (db.getNodes().get(i).getIndex()+1)  );   //afisarea nodurilor
                        
                        for( int j = 0 ; j < db.getNodes().get(i).getInstances().size(); j ++ )
                        {   
                            
                            Entitate e = db.getEntities().get(0);
                            for(Entitate k : db.getEntities() )      
                    
                                if (k.getName().equals(db.getNodes().get(i).getInstances().get(j).getEntity_name() )) //gasim entiatea din care face parte instanta
                                {
                                    e = k;
                                    instance_found = true;
                                } 
                            
                            output.print(e.getName()+ " ");
                            for( int k = 0 ; k < e.getNo_attributes()-1 ; k ++ )   //afisarea atributelor
                                output.print(e.getValue().get(k) + ":" + 
                                db.getNodes().get(i).getInstances().get(j).getA_values().get(k) +" ");
                            
                                     //Urmeaza sa printam si ultimul atribut si tipul sau
                                     
                                output.println(e.getValue().get(e.getNo_attributes()-1) + ":" + 
                                db.getNodes().get(i).getInstances().get(j).getA_values().get(e.getNo_attributes()-1 ));
                        }
                    }
                    if( instance_found == false)
                        output.println("EMPTY DB");
                }
                else if( cmd.equals("CLEANUP") )
                {   
                    String db_name = "NULL";
                    String timestamp = "NULL";
                    if( string.hasMoreTokens())
                     db_name = string.nextToken();
                    timestamp = string.nextToken();
                    
                    long ts = Long.parseLong(timestamp);
                    
                    for( Nod n :db.getNodes() )
                        for( int i = 0 ; i < n.getInstances().size() ; i++ )
                        {      
                           
                             if( n.getInstances().get(i).getTimestamp() < ts )
                             {  
                                 n.getInstances().remove(i);
                                 i--;                          //eliminam si decrementam contorul
                             }
                             
                        }
                                 
                }
                
            }
        input.close();
        output.close(); 
        }
        catch(IOException ex)
        {
            System.out.println("File not found!");
        }
        
    }
    
}
