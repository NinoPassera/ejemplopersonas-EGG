/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursoegg.ejemplopersonas.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nahue
 */
@Controller
@RequestMapping("/")
public class mainController {
    
    @GetMapping("")
    public String  index(Model model ){
         
       
        
        return "index";
        
    }
    

    
}
