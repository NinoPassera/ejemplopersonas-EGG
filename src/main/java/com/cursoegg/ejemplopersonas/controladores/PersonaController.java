/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursoegg.ejemplopersonas.controladores;

import com.cursoegg.ejemplopersonas.entidades.Persona;
import com.cursoegg.ejemplopersonas.servicios.CiudadService;
import com.cursoegg.ejemplopersonas.servicios.PersonaServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Nahue
 */
@Controller
@RequestMapping("/persona")
public class PersonaController {

  @Autowired
  private PersonaServicio personaServicio;

  @Autowired
  private CiudadService ciudadService;

  @GetMapping("/list")
  public String listarPersonas(Model model, @RequestParam(required = false) String q) {
    if (q != null) {
      model.addAttribute("personas", personaServicio.listAllByQ(q));
    } else {
      model.addAttribute("personas", personaServicio.listAll());
    }

    return "persona-list";
  }

  @GetMapping("/form")
  public String crearPersona(Model model, @RequestParam(required = false) String id) {
    if (id != null) {
      Persona optional = personaServicio.findById(id);
      if (optional != null) {
        model.addAttribute("persona", optional);
      } else {
        return "redirect:/persona/list";
      }
    } else {
      model.addAttribute("persona", new Persona());
    }
    model.addAttribute("ciudades", ciudadService.listAll());
    return "persona-form";
  }

  @PostMapping("/save")
  public String guardarPersona(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Persona persona,@RequestParam String contraseña2,@RequestParam MultipartFile archivo) {
    try {
        
      personaServicio.save(persona,contraseña2,archivo);
      redirectAttributes.addFlashAttribute("success", "Persona guardada con éxito");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/persona/list";
  }
  
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/delete")
  public String eliminarPersona(@RequestParam String id) {
    personaServicio.deleteById(id);
    return "redirect:/persona/list";
  }
}
