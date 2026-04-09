package com.videocartago.renting.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import com.videocartago.renting.domain.Actor;
import com.videocartago.renting.domain.Genero;
import com.videocartago.renting.domain.Pelicula;

@SpringBootTest
public class PeliculaDataTest {

    @Autowired
	private PeliculaData peliculaData;
    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Test
	@Sql (scripts = "/save_pelicula_con_actores.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	public void save_pelicula_con_actores() {
		// Assert MEJORADO CON UNA INYECCIÓN DE REGISTROS PARA MEJORAR LA PRUEBA UNITARIA
		//	IDENT_CURRENT() returns the last identity value generated for a specific table, regardless of the scope.
		// Retrieve generated genero_id
        Integer generoId = jdbcTemplate.queryForObject("SELECT IDENT_CURRENT('Genero')", Integer.class);

        // Retrieve generated actor1_id and actor2_id
        Integer actorId1 = jdbcTemplate.queryForObject("SELECT IDENT_CURRENT('Actor')-1", Integer.class);
        Integer actorId2 = jdbcTemplate.queryForObject("SELECT IDENT_CURRENT('Actor')", Integer.class);

		List<Actor> actores = new LinkedList<>();
		actores.add(new Actor(actorId1, null, null));
		actores.add(new Actor(actorId2, null, null));
        
		Pelicula pelicula = new Pelicula(0, "The Matrix", true, true, 
        new Genero(generoId, null), actores);
		
		//Act
		assertDoesNotThrow(() -> peliculaData.save(pelicula));
		// Assert
		assertNotEquals(0, pelicula.getPeliculaId());
	}

}
