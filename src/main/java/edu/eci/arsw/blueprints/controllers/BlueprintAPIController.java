/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 *
 * @author hcadavid
 */

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    @Qualifier("Service")
    private BlueprintsServices bps;

    
    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBluePrintsWithJSON(@PathVariable("author") String author, @PathVariable("bpname") String bpname, @RequestBody Blueprint blueprint) {
        try {
            bps.putBlueprint(author, bpname,new ArrayList<>(blueprint.getPoints()));
            return new ResponseEntity<>("Pero esta maquina para actualizar de donde salio", HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Sigue intentanto algun dia aprenderas algo", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/creandoAndo", method = RequestMethod.POST)
    public ResponseEntity<?> addBluePrintsWithJSON(@RequestBody Blueprint blueprint) {
        try {
            bps.addNewBlueprint(blueprint);
            return new ResponseEntity<>("Se creo de forma satisfactoria el plano Ta fachero", HttpStatus.ACCEPTED);
        } catch ( BlueprintPersistenceException be) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, be.getMessage(), be);
            return new ResponseEntity<>("Toca aprender a crear planos por que no fue esta vez", HttpStatus.BAD_REQUEST);
        }
    }
    


    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.GET)
    public ResponseEntity<?> getBluePrintsByAuthorAndNameInJSON(@PathVariable("author") String author, @PathVariable("bpname") String bpname) {
        try {
            Blueprint results = bps.getBlueprint(author, bpname);
            if (results == null) {
                return new ResponseEntity<>("No Se encontro lo que se pide, rectifique su busqueda", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(results, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Si no sabe buscar de esta forma pues aprenda", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> getBluePrintsByAuthorInJSON(@PathVariable("author") String author) {
        try {
            Set<Blueprint> results = bps.getBlueprintsByAuthor(author);
            if (results.isEmpty()) {
                return new ResponseEntity<>("No Se encontro lo que se pide, rectifique su busqueda",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(results, HttpStatus.ACCEPTED);
        } catch ( BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Esa busqueda no se realizo bien aprenda a buscar", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> BlueprintGetRecurso() {
        try {
            Set<Blueprint> data = bps.getAllBlueprints();
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintsServices.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Pero como le queda grande consultar si es re papas.", HttpStatus.BAD_REQUEST);
        }
    }


}
