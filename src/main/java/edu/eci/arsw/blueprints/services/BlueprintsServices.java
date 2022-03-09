/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.BluePrintsFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;



/**
 *
 * @author hcadavid
 */
@Service
@Qualifier("Service")
public class BlueprintsServices {
   
    @Autowired
    @Qualifier("Memory")
    BlueprintsPersistence bpp;

    @Autowired
    @Qualifier("Redundancia")
    BluePrintsFilter filtro;
    

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException{
        bpp.saveBlueprint(bp);
    }
    
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException{
        return filtrado(bpp.getBlueprints());
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException{
        return filtro.filtrado(bpp.getBlueprint(author, name));
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        return filtrado(bpp.getBlueprintsByAuthor(author));
    }
    

    private Set<Blueprint> filtrado(Set<Blueprint> bp){
        Set<Blueprint> temp = new HashSet<>();
        for(Blueprint a:bp){
            temp.add(filtro.filtrado(a));
        }
        return temp;
    }

    public void putBlueprint(String author,String name,  List<Point> points)throws BlueprintNotFoundException{
        bpp.putBlueprint( author, name, points);
    }
}
