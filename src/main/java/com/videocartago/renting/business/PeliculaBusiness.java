package com.videocartago.renting.business;

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
 
}
