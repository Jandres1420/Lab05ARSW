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
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
    @Autowired
    private BlueprintsServices blueprintsServices;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllBluePrints() {
        try {
            //String gsonToString = this.stringToGson(blueprintsServices.getAllBlueprints());
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(blueprintsServices.getAllBlueprints(), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error 404", HttpStatus.NOT_FOUND);
        }


    }

    @RequestMapping(method = RequestMethod.GET, value ="/authors/{authors}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String authors){
        try {
            return new ResponseEntity<>(blueprintsServices.getBlueprintsByAuthor(authors),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error 404", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/authors/{author}/bpname/{bpname}")
    public ResponseEntity<?> getBluePrint(@PathVariable String author, @PathVariable String bpname ){
        try {
            return new ResponseEntity<>(blueprintsServices.getBlueprint(author,bpname), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("Error 404", HttpStatus.NOT_FOUND);
        }

    }

    //POST
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostRecursoPlanos(@RequestBody Blueprint blueprint) {
        try {
            blueprintsServices.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN);
        }
    }

    //PUT
    @RequestMapping(value = "/authors/{author}/blueprints/{bpname}", method = RequestMethod.PUT)
    public ResponseEntity<?> putBlueprintsByAuthor(@PathVariable String author, @PathVariable String bpname, @RequestBody Blueprint blueprint) {
        blueprintsServices.deleteAuthorsBpname(author,bpname);
        blueprintsServices.addNewBlueprint(blueprint);
        return ResponseEntity.ok(blueprint);
    }

}
