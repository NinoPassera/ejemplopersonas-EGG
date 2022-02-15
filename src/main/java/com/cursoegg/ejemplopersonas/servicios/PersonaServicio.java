package com.cursoegg.ejemplopersonas.servicios;

import com.cursoegg.ejemplopersonas.entidades.Ciudad;
import com.cursoegg.ejemplopersonas.entidades.Foto;
import com.cursoegg.ejemplopersonas.entidades.Persona;
import com.cursoegg.ejemplopersonas.excepciones.Role;

import com.cursoegg.ejemplopersonas.excepciones.WebException;
import com.cursoegg.ejemplopersonas.repositories.PersonaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PersonaServicio implements UserDetailsService{

    @Autowired
    private PersonaRepository personaRepository;
    
    @Autowired
    private CiudadService ciudadService;
    
    @Autowired
    private FotoService fotoService;

    public void validacion() {

    }

    public Persona validacion(Persona persona,String contraseña2) throws WebException {
         if (persona.getNombre().isEmpty() || persona.getNombre() == null || persona.getNombre().length() < 3) {
            throw new WebException(" El nombre no puede estar vacío o tener menos de 3 caracteres");
        }
        if (persona.getApellido().isEmpty() || persona.getApellido() == null) {
            throw new WebException(" El apellido no puede estar vacío");
        }
        if (persona.getEdad() == null || persona.getEdad() < 1) {
            throw new WebException(" La edad no puede estar vacía o ser menor a 1");
        }
        if (persona.getCiudad() == null) {
            throw new WebException(" La ciudad no puede ser nula");
        }if (contraseña2==null) {
            
            throw new WebException(" La contraseña no puede ser nula");
            
        }if (persona.getContraseña() == null) {
            
             throw new WebException(" La contraseña no puede ser nula");
            
        }if (!(contraseña2.equals(persona.getContraseña())) ) {
            
            throw new WebException(" La contraseña tienen que ser iguales");
            
        }
        
        return persona;
    }

    @Transactional
    public Persona save(Persona persona,String contraseña2,MultipartFile archivo) throws WebException {
       
        Persona personaFinal = validacion(persona,contraseña2);
        Foto foto = fotoService.GuardarFoto(archivo);
        
        personaFinal.setFoto(foto);
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        personaFinal.setContraseña(encoder.encode(personaFinal.getContraseña()));
        personaFinal.setRol(Role.USER);
        return personaRepository.save(personaFinal);
        
    }

    @Transactional
    public Persona save(String nombre, String apellido, Integer edad) {
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setEdad(edad);
        return personaRepository.save(persona);
    }

    public List<Persona> listAll() {
        return personaRepository.findAll();
    }

    public List<Persona> listAllByQ(String q) {
        return personaRepository.findAllByQ("%" + q + "%");
    }

    public List<Persona> listAllbyCiudad(String nombre) {
        return personaRepository.findAllByCiudad(nombre);
    }

    public Persona findById(String id) {
        Optional<Persona> respuesta = personaRepository.findById(id);
        if (respuesta.isPresent()){
            return respuesta.get();
        }else{
            return null;
        }
       
    }

    @Transactional
    public void delete(Persona persona) {
        personaRepository.delete(persona);
    }

    @Transactional
    public void deleteFieldCiudad(Ciudad ciudad) {
        List<Persona> personas = listAllbyCiudad(ciudad.getNombre());
        for (Persona persona : personas) {
            persona.setCiudad(null);
        }
        personaRepository.saveAll(personas);
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Persona> optional = personaRepository.findById(id);
        if (optional.isPresent()) {
            personaRepository.delete(optional.get());
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Persona persona = personaRepository.findByName(username);
            
            List<GrantedAuthority> authorities = new ArrayList<>();
            
            authorities.add(new SimpleGrantedAuthority("ROLE_"+persona.getRol()));
            
            
            return new User(username,persona.getContraseña(),authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("EL usuario solicitado no existe");
        }
    }

   

}
