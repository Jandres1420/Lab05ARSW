/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
    @Autowired
    @Qualifier
    private BlueprintsServices blueprintsServices;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllBluePrints() {
        try {
            String gsonToString = this.stringToGson(blueprintsServices.getAllBlueprints());
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(new Gson().toJson(gsonToString), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }


    }

   /** @RequestMapping(method = RequestMethod.GET,value = "/authors/{author}/bpname/{bpname}")
    public ResponseEntity<?> getBlueprints(@PathVariable String author, @PathVariable String bpname ){
        try {
            Set<Blueprint>blueprints= new Set<Blueprint>(blueprintsServices.getBlueprint(author,bpname));
            String gsonToString = this.stringToGson(new HashSet<Blueprint>(blueprintsServices.getBlueprint(author,bpname)));
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }

    }**/

    @RequestMapping(method = RequestMethod.GET, value ="/authors/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String authour){
        try {
            String gsonToString = this.stringToGson(blueprintsServices.getBlueprintsByAuthor(authour));
            return new ResponseEntity<>(new Gson().toJson(gsonToString),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value =)
    private String stringToGson(Set<Blueprint> allBlueprints){
        List<Blueprint> blueprintList = new ArrayList<>(allBlueprints);
        String convertidor ="{\"blueprints\" : ";
        for(Blueprint bs: blueprintList){
            convertidor += "Author : " + bs.getAuthor() + ", Name: " + bs.getName() + " , Points :" + bs.getPoints();
        }
        convertidor += "}";
        return convertidor;
    }


}
