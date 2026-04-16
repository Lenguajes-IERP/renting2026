package com.videocartago.renting.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.videocartago.renting.domain.Actor;

@Repository
public class ActorData {
	    @Autowired
	    private JdbcTemplate jdbcTemplate;

	    public List<Actor> findAll() {
	        String selectSql = "SELECT actor_id, nombre_actor, apellidos_actor FROM Actor";
	        List<Actor> actores = jdbcTemplate.query(selectSql, new ActorRowMapper());
	        return actores;
	    }

	    private static final class ActorRowMapper implements RowMapper<Actor> {
	        @Override
	        public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
	            return new Actor(rs.getInt("actor_id"), rs.getString("nombre_actor"), 
	            		rs.getString("apellidos_actor"));
	        }
	    }
}