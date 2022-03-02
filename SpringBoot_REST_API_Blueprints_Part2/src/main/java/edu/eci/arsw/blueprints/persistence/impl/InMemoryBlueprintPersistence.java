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
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author hcadavid
 */
@Repository
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Point[] pts2=new Point[]{new Point(141, 141),new Point(116, 116)};
        Point[] pts3=new Point[]{new Point(20, 20),new Point(30, 30)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_",pts);
        Blueprint bp2=new Blueprint("Espinoza", "xd",pts2);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()),bp2);
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
        if(blueprints.size() <= 1){
            throw new BlueprintNotFoundException("there is no blueprint");
        }
        Blueprint blueprint = blueprints.get(new Tuple<>(author, bprintname));
        if(blueprint == null){
            throw new BlueprintNotFoundException("blueprint doesn't exist");
        }
        return blueprint;

    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        if(blueprints.size() <= 1){
            throw new BlueprintNotFoundException("there is no blueprint");
        }
        Set<Blueprint> blueprintSet = new HashSet<>();
        for(Map.Entry<Tuple<String,String>,Blueprint> entry : blueprints.entrySet()){
            if (entry.getKey().getElem1().equals(author)){
                blueprintSet.add(entry.getValue());
            }

        }
        if(blueprintSet.isEmpty()){
            throw new BlueprintNotFoundException("blueprints from this author doesn't exist");
        }
        return blueprintSet;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        if(blueprints.size() <= 1){
            throw new BlueprintNotFoundException("there is no blueprint");
        }
        return blueprints.values().stream().collect(Collectors.toSet());
    }


}
