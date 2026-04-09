package com.videocartago.renting.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videocartago.renting.data.PeliculaData;
import com.videocartago.renting.domain.Pelicula;

@Service
public class PeliculaBusiness {
    @Autowired
    private PeliculaData peliculaData;

    public List<Pelicula> findMoviesByTitleOrGenre(String title, String genre) {
        return peliculaData.findMoviesByTitleOrGenre(title, genre);
    }
    public Pelicula save(Pelicula pelicula) throws SQLException{
		peliculaData.save(pelicula);
        return pelicula;
	}

}
