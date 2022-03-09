/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hcadavid
 */

@Repository
@Qualifier(value="Memory")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final ConcurrentHashMap<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap <>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_",pts);
        Point[] pts0=new Point[]{new Point(0, 0),new Point(0, 0)};
        Blueprint bp0=new Blueprint("john", "thepaint",pts0);
        Point[] pts1=new Point[]{new Point(11, 11),new Point(11, 1)};
        Blueprint bp1=new Blueprint("johnan", "thepaint1",pts1);
        Point[] pts2=new Point[]{new Point(21, 21),new Point(21, 21)};
        Blueprint bp2=new Blueprint("Sebas", "thepaint2",pts2);
        Point[] pts3=new Point[]{new Point(4, 40),new Point(40, 40)};
        Blueprint bp3=new Blueprint("john", "thepaint3",pts3);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        blueprints.put(new Tuple<>(bp0.getAuthor(),bp0.getName()), bp0);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);

        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> temp = new HashSet<>();
        for (Map.Entry<Tuple<String,String>,Blueprint> set:blueprints.entrySet()) {
            if(set.getValue().getAuthor().equals(author))temp.add(set.getValue());
        }
        return temp;
    }

    @Override
    public Set<Blueprint> getBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> temp = new HashSet<>();
        for (Map.Entry<Tuple<String,String>,Blueprint> set:blueprints.entrySet()) {
            temp.add(set.getValue());
        }
        return temp;
    }

    @Override
    public void putBlueprint(String author, String name, List<Point> points) throws BlueprintNotFoundException {
        blueprints.get(new Tuple<>(author, name)).setPoints(points);
    }

    
    
    
}
