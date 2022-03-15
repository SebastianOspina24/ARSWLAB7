/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import java.util.List;
import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

/**
 *
 * @author hcadavid
 */
public interface BlueprintsPersistence {
    
    /**
     * 
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     *    or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;

    /**
     * 
     * @param author
     * @param bprintname
     * @return
     * @throws BlueprintNotFoundException
     */
    public void deleteBlueprint(String author,String name)throws BlueprintPersistenceException;
    /**
     * 
     * @param author blueprint's author
     * @param bprintname blueprint's author
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String bprintname) throws BlueprintNotFoundException;

    /**
     * Retorna conjunto de blueprints de un autor
     * @param author autor a buscar
     * @return set de blueprints del autor
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException;

    /**
     * Retorna el conjunto de todos los blueprints
     * @return conjunto de todos los blueprints
     * @throws BlueprintNotFoundException
     */
    public Set<Blueprint> getBlueprints()throws BlueprintNotFoundException;
    

    public void putBlueprint(String author,String name, List<Point> points)throws BlueprintNotFoundException;
}
