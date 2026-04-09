package com.videocartago.renting.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.videocartago.renting.domain.Genero;

@Repository
public class GeneroData {
    @Autowired
    private  JdbcTemplate jdbcTemplate;

    public List<Genero> findAll() {
         SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("Genero_Get") // Nombre del procedimiento almacenado
                // Aquí ya no debería marcar error
                .returningResultSet("generosList", new GeneroRowMapper());

        Map<String, Object> out = jdbcCall.execute(Collections.emptyMap());
        return (List<Genero>) out.get("generosList");
    }
    private static final class GeneroRowMapper implements RowMapper<Genero> {
        @Override
        public Genero mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genero(
                rs.getInt("genero_id"), 
                rs.getString("nombre_genero")
            );
        }
    }
}