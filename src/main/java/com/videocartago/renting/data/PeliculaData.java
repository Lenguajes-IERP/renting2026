package com.videocartago.renting.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.videocartago.renting.domain.Actor;
import com.videocartago.renting.domain.Pelicula;

@Repository
public class PeliculaData {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

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
                INNER JOIN Genero g 
                    ON p.genero_id = g.genero_id
                LEFT JOIN PeliculaActor pa 
                    ON p.pelicula_id = pa.pelicula_id
                LEFT JOIN Actor a 
                    ON pa.actor_id = a.actor_id
                WHERE LOWER(p.titulo) LIKE ?
                or LOWER(g.nombre_genero) LIKE ?
                """;
             title = title.toLowerCase();
             genre = genre.toLowerCase();
            String titleLike = (title == null || title=="" ? "" : "%" + title.trim() + "%");
            String genreLike = (genre == null || genre=="" ? "" : "%" + genre.trim() + "%");
            // Pasamos el SQL, la instancia del extractor y los parámetros
                return jdbcTemplate.query(
                    sqlSelect, 
                    new PeliculaExtractor(), 
                    titleLike, 
                    genreLike
                );
    }

     @Transactional  // The @Transactional annotation bounds the method execution in a transaction context as it is performing a database update.
	public Pelicula save(Pelicula pelicula) throws SQLException{
        // 
			SimpleJdbcCall simpleJdbcCallPelicula = new SimpleJdbcCall(jdbcTemplate).
					withCatalogName("dbo").
					withProcedureName("InsertPelicula").withoutProcedureColumnMetaDataAccess().
					declareParameters(new SqlOutParameter("@pelicula_id", Types.INTEGER)).
					declareParameters(new SqlParameter("@titulo", Types.VARCHAR)).
					declareParameters(new SqlParameter("@subtitulada", Types.BIT)).
					declareParameters(new SqlParameter("@estreno", Types.BIT)).
					declareParameters(new SqlParameter("@genero_id", Types.INTEGER));
			Map<String, Object> outParameters = simpleJdbcCallPelicula.execute(pelicula.getTitulo(), pelicula.isSubtitulada(), pelicula.isEstreno(), pelicula.getGenero().getGeneroId());
			pelicula.setPeliculaId(Integer.parseInt(outParameters.get("@pelicula_id").toString()));
			
			SimpleJdbcCall simpleJdbcCallPeliculaActor = new SimpleJdbcCall(jdbcTemplate).
					withCatalogName("dbo").
					withProcedureName("InsertPeliculaActor").withoutProcedureColumnMetaDataAccess().
					declareParameters(new SqlParameter("@pelicula_id", Types.INTEGER)).
					declareParameters(new SqlParameter("@actor_id", Types.INTEGER));
			for(Actor actor:pelicula.getActores())
				simpleJdbcCallPeliculaActor.execute(pelicula.getPeliculaId(), actor.getActorId());
		return pelicula;
	}

    public void remove(int codPelicula) throws SQLException {
		Connection conexion = null;
		try {
			conexion = dataSource.getConnection();
			conexion.setAutoCommit(false);
			String sqlDeletePeliculaActor = "DELETE FROM PeliculaActor "
					+ "WHERE pelicula_id = ?";
			String sqlDeletePelicula = "DELETE FROM Pelicula "
					+ "WHERE pelicula_idxxx = ?";

			// Eliminar los registros asociados en la tabla Pelicula_Actor
			PreparedStatement statementPeliculaActor =
					conexion.prepareStatement(sqlDeletePeliculaActor);
			statementPeliculaActor.setInt(1, codPelicula);
			statementPeliculaActor.executeUpdate();
			statementPeliculaActor.close();

			// Eliminar los registros asociados en la tabla Pelicula
			PreparedStatement statementPelicula =
					conexion.prepareStatement(sqlDeletePelicula);
			statementPelicula.setInt(1, codPelicula);
			statementPelicula.executeUpdate();
			statementPelicula.close();

			conexion.commit();
		} catch (SQLException e) {
			if (conexion!=null) {
					conexion.rollback();
				}
			throw new SQLException(e);
		}finally {
			if (conexion != null)
				conexion.close();
		}
			
	}//remove
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
                pelicula.getGenero().setNombreGenero(rs.getString("nombre_genero"));
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
