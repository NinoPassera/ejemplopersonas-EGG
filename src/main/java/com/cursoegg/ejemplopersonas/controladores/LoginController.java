/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursoegg.ejemplopersonas.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author cuent
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    
    @GetMapping("")
    public String login(Model model,@RequestParam(required = false) String error,
            @RequestParam(required = false)String logout){
        if (error != null) {
            model.addAttribute("error", "el usuario o la contraseña ingresadas no son validas");
        }
        
        if (logout != null) {
             model.addAttribute("logout", "");
        }
        
        return "login";
    }
    
}
