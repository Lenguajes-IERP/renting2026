package com.videocartago.renting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.videocartago.renting.domain.Actor;
import com.videocartago.renting.domain.Genero;
import com.videocartago.renting.domain.Pelicula;
import com.videocartago.renting.dto.PeliculaCreationDTO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PeliculaMapper {
   @Mapping(source = "genero.generoId", target = "genero.generoId")
   @Mapping(target = "actores", expression = "java(mapActors(peliculaCreationDTO.getActores()))")
   Pelicula toPelicula(PeliculaCreationDTO peliculaCreationDTO);
   
   default List<Actor> mapActors(List<PeliculaCreationDTO.ActorDTO> actorDTOs) {
       if (actorDTOs == null) {
           return null;
       }
       return actorDTOs.stream()
               .map(actorDTO -> {
                   Actor actor = new Actor();
                   actor.setActorId(actorDTO.getActorId());
                   return actor;
               }).collect(java.util.stream.Collectors.toList());
   }
   default Genero mapGenero(PeliculaCreationDTO.GeneroDTO generoDTO){
       if(generoDTO == null){
           return null;
       }
       Genero genero = new Genero();
       genero.setGeneroId(generoDTO.getGeneroId());
       return genero;
   }
}
