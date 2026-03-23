package com.videocartago.renting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {
   @RequestMapping(value="/", method=RequestMethod.GET)
   public String welcome(Model model) {
        model.addAttribute("miNombre", "Alvaro Mena");

        String[] peliculas = {
            "Avengers",
            "Batman",
            "Spiderman",
            "Star Wars",
            "Interstellar"
        };
 
        model.addAttribute("peliculas", peliculas);
       return "welcome"; // corresponde al nombre de la plantilla
   }
}

