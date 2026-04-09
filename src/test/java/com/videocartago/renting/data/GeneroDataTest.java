package com.videocartago.renting.data;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.videocartago.renting.domain.Genero;

@SpringBootTest
class GeneroDataTest {
	@Autowired
	private GeneroData generoData;
	@Test
	@Sql (scripts = "/findAll_recupera_todos_los_generos.sql",  
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
        
	void findAll_recupera_todos_los_generos() {
		//Act
		List<Genero> generos = generoData.findAll();
		int cantidadRegistrosRetornados = generos.size();
		//Arrange
		int cantidadRegistrosEsperados = 10;
		assertEquals(cantidadRegistrosEsperados, 
            cantidadRegistrosRetornados);
	}
}