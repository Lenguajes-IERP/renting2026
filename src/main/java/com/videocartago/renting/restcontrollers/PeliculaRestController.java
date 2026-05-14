package com.videocartago.renting.restcontrollers;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.videocartago.renting.business.PeliculaBusiness;
import com.videocartago.renting.domain.Pelicula;
import com.videocartago.renting.dto.PeliculaCreationDTO;
import com.videocartago.renting.mapper.PeliculaMapper;


@RestController
@RequestMapping(value = "/peliculas")
@CrossOrigin(origins = "http://localhost:4200")
public class PeliculaRestController {
	@Autowired
	private PeliculaBusiness peliculaBusiness;
	
	@Autowired
   private PeliculaMapper peliculaMapper; // Inject the mapper
	
	
    @PostMapping("/")
       public ResponseEntity<?> createPelicula(@Validated @RequestBody PeliculaCreationDTO peliculaDTO,
                 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
                    List<FieldError> errors = bindingResult.getFieldErrors();
                    List<String> errorMessages = errors.stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
                    return ResponseEntity.badRequest().body(errorMessages);
                 }
		Pelicula pelicula = peliculaMapper.toPelicula(peliculaDTO);
                try {
			this.peliculaBusiness.save(pelicula);
		} catch (SQLException e) {
			// Nunca dejes un catch vacío o solo con printStackTrace en producción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error al guardar la película: " + e.getMessage());
		}
      // TODO what to return..revisarlo
      return ResponseEntity.status(HttpStatus.CREATED).body(pelicula.getPeliculaId());
   }
   @GetMapping(value="/" ) // TODO PENDIENTE
	public String findMovies(@RequestParam("titulo") String titulo,
			@RequestParam("genero") String genero) {  
                var peliculas = peliculaBusiness.findMoviesByTitleOrGenre(titulo, genero);
		return "findMovies";
	}
	
}
