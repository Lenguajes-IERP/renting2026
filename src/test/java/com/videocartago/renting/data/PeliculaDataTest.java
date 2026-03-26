package com.videocartago.renting.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class PeliculaDataTest {

    @Autowired
	private PeliculaData peliculaData;

    //Gerkhin
    @Test
    @DisplayName("Debe retornar la(s) película(s) cuando el título y el género existen en la base de datos")
    @Transactional // Para que los datos se borren automáticamente al terminar el test
    @Sql(scripts = "/insert_peliculas_con_actores.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenExistingMovieAndGenre_whenFindMoviesByTitleOrGenre_thenReturnsMatchingMovies() {
        // Arrange
            String title = "Women";
            String genre = "Drama";
        // Act
            var peliculas = peliculaData.findMoviesByTitleOrGenre(title, genre); // MUT method under test
        // Assert

        String expectedTitle = "Women";
        String expectedGenre = "Drama";
        assertNotNull(peliculas);
		assertTrue(!peliculas.isEmpty());

		assertTrue(peliculas.stream().anyMatch(p -> p.getTitulo().contains(expectedTitle) ||
         p.getGenero().getNombreGenero().contains(expectedGenre)));
        
         
    }

    @Test
    @DisplayName("Debe retornar películas cuando solo el título existe y el género es nulo")
    @Transactional
    @Sql(scripts = "/insert_peliculas_con_actores.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenExistingTitleAndNullGenre_whenFindMoviesByTitleOrGenre_thenReturnsMatchingMovies() {
    // Arrange
    String title = "Women";
    String genre = null;
 
    // Act
    var peliculas = peliculaData.findMoviesByTitleOrGenre(title, genre);
 
    // Assert
    assertNotNull(peliculas, "La lista no debería ser nula");
    assertFalse(peliculas.isEmpty(), "La lista no debería estar vacía cuando el título existe");
    assertTrue(
        peliculas.stream().allMatch(p -> p.getTitulo().toLowerCase().contains(title.toLowerCase())),
        "Todas las películas deben coincidir con el título"
    );
}
 
    @Test
    @DisplayName("Debe retornar películas cuando solo el género existe y el título es nulo")
    @Transactional
    @Sql(scripts = "/insert_peliculas_con_actores.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenNullTitleAndExistingGenre_whenFindMoviesByTitleOrGenre_thenReturnsMatchingMovies() {
    // Arrange
    String title = null;
    String genre = "Drama";
 
    // Act

    var peliculas = peliculaData.findMoviesByTitleOrGenre(title, genre);
 
    // Assert
    assertNotNull(peliculas, "La lista no debería ser nula");
    assertFalse(peliculas.isEmpty(), "La lista no debería estar vacía cuando el género existe");
    assertTrue(
        peliculas.stream().allMatch(p -> p.getGenero().getNombreGenero().equalsIgnoreCase(genre)),
        "Todas las películas deben coincidir con el género"
    );
}
    @Test
    @DisplayName("Revisar cuando se mandan datos nulos")
    @Transactional
    @Sql(scripts = "/insert_peliculas_con_actores.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenNullTitleAndGenre_whenFindMoviesByTitleOrGenre_thenReturnsEmptyList() {
 
        // Arrange
        String title = "";
        String genre = "";
 
        // Act
        var peliculas = peliculaData.findMoviesByTitleOrGenre(title, genre);
 
        // Assert
        assertNotNull(peliculas);
        assertTrue(peliculas.isEmpty(), "La lista de películas debería estar vacía");
    }
 
    @Test
    @DisplayName("Debe retornar la(s) película(s) cuando el título y el género existen en la base de datos")
    @Transactional // Para que los datos se borren automáticamente al terminar el test
    @Sql(
        scripts = "/insert_peliculas_con_actores.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(separator = "GO")
    )
public void  prueba1() {
    // Arrange
    String title = "Dune";
    String genre = "NohayGenero";
   
   // Act
    var peliculas = peliculaData.findMoviesByTitleOrGenre(title, genre);

 
    // Assert
    String notExpectedGenre = "NohayGenero";
    String expectedTitle = "Dune";
    assertTrue(peliculas.stream().anyMatch(p -> p.getTitulo().contains(expectedTitle) &&  !p.getGenero().getNombreGenero().contains(notExpectedGenre)));
    assertNotNull(peliculas);
    assertFalse(peliculas.isEmpty());
 
    }
 

    @Test
    @DisplayName("Debe retornar películas cuando existe el género aunque no exista el título")
    void givenNonExistingMovieAndExistingGenre_whenFindMoviesByTitleOrGenre_thenReturnsMoviesMatchingGenre() {
        String title = "NonExistingTitle";
        String genre = "Drama";
 
        var peliculas = peliculaData.findMoviesByTitleOrGenre(title, genre);
 
        assertNotNull(peliculas);
        assertFalse(peliculas.isEmpty());
 
        assertTrue(!peliculas.stream().allMatch(p ->
            p.getTitulo().contains(title) &&
            (p.getGenero() != null && p.getGenero().getNombreGenero().contains(genre))
        ));
 
        assertTrue(peliculas.stream().anyMatch(p ->
            p.getGenero() != null && p.getGenero().getNombreGenero().equals(genre)
        ));
    }
}
