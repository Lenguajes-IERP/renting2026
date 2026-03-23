package com.videocartago.renting.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.videocartago.renting.domain.Actor;
import com.videocartago.renting.domain.Pelicula;

@Repository
public class PeliculaData {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Pelicula> findMoviesByTitleOrGenre(String title, String genre) {
        String sqlSelect = """
                SELECT 
                    p.pelicula_id,
                    p.titulo,
                    p.genero_id,
                    g.nombre_genero,
                    p.subtitulada,
                    p.estreno,
                    pa.actor_id,
                    a.nombre_actor,
                    a.apellidos_actor
                FROM Pelicula p
                LEFT JOIN Genero g 
                    ON p.genero_id = g.genero_id
                LEFT JOIN PeliculaActor pa 
                    ON p.pelicula_id = pa.pelicula_id
                LEFT JOIN Actor a 
                    ON pa.actor_id = a.actor_id
                WHERE p.titulo LIKE ?
                or g.nombre_genero LIKE ?
                """;
                String titleLike = "%" + title + "%";
                String genreLike = "%" + genre + "%";
            // Pasamos el SQL, la instancia del extractor y los parámetros
                return jdbcTemplate.query(
                    sqlSelect, 
                    new PeliculaExtractor(), 
                    titleLike, 
                    genreLike
                );
    }
}
class PeliculaExtractor implements ResultSetExtractor<List<Pelicula>> {
    @Override
     public List<Pelicula> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Pelicula> map = new HashMap<>();
        Pelicula pelicula = null;
        while (rs.next()) {
            int peliculaId = rs.getInt("pelicula_id");
            pelicula = map.get(peliculaId);
            if (pelicula == null) {
                pelicula = new Pelicula();
                pelicula.setPeliculaId(peliculaId);
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setSubtitulada(rs.getBoolean("subtitulada"));
                pelicula.setEstreno(rs.getBoolean("estreno"));
                pelicula.getGenero().setGeneroId(rs.getInt("genero_id"));
                pelicula.getGenero().setNombre_genero(rs.getString("nombre_genero"));
                map.put(peliculaId, pelicula);
            }//if
            int actorId = rs.getInt("actor_id");
            if (actorId >0) {
                Actor actor = new Actor();
                actor.setActorId(actorId);
                actor.setNombreActor(rs.getString("nombre_actor"));
                actor.setApellidosActor(rs.getString("apellidos_actor"));
                pelicula.getActores().add(actor);
            }//if
        } //while
        return new ArrayList<Pelicula>(map.values());
    } //extract Data  
}
