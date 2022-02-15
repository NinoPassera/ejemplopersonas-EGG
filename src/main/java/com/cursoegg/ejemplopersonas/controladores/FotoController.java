/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursoegg.ejemplopersonas.controladores;

import com.cursoegg.ejemplopersonas.entidades.Persona;
import com.cursoegg.ejemplopersonas.excepciones.WebException;
import com.cursoegg.ejemplopersonas.servicios.PersonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author cuent
 */
@Controller
@RequestMapping("/foto")
public class FotoController {

    @Autowired
    private PersonaServicio personaService;

    @GetMapping("/usuario")
    private ResponseEntity<byte[]> fotoUsuario(@RequestParam String id) {

        try {
            System.out.println(id);
            Persona persona = personaService.findById(id);
            if (persona.getFoto() == null) {
                throw new WebException("El usuario no tiene una foto");
            }
            byte[] foto = persona.getFoto().getContenido();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
