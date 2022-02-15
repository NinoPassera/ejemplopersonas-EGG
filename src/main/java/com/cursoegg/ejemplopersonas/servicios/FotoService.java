/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursoegg.ejemplopersonas.servicios;

import com.cursoegg.ejemplopersonas.entidades.Foto;
import com.cursoegg.ejemplopersonas.excepciones.WebException;
import com.cursoegg.ejemplopersonas.repositories.FotoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author cuent
 */
@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    public Foto GuardarFoto(MultipartFile archivo) throws WebException {

        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepository.save(foto);
            } catch (Exception e) {

                System.out.println(e.getMessage());
                

            }

        }
        return null;
    }

    public Foto actualizarFoto(String idFoto, MultipartFile archivo) throws WebException {

        if (archivo != null) {
            try {
                Foto foto = new Foto();
                if (idFoto != null) {

                    Optional<Foto> respuesta = fotoRepository.findById(idFoto);
                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                    foto.setMime(archivo.getContentType());
                    foto.setNombre(archivo.getName());
                    foto.setContenido(archivo.getBytes());
                    return fotoRepository.save(foto);
                } 

            } catch (Exception e) {
                 System.out.println(e.getMessage());
            }
          
        } 
        return null;
        

    }

}
