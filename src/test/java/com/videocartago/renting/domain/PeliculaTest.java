package com.videocartago.renting.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PeliculaTest {

    @Test
    public void constructor_default_works() {
        Pelicula pelicula = new Pelicula();
        pelicula.setPeliculaId(1);
        pelicula.setTitulo("Inception");    
        pelicula.setSubtitulada(true);
        pelicula.setEstreno(false);
        pelicula.getGenero().setGeneroId(1);

        assert pelicula != null;
    }


}
