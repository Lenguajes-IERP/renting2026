USE videorent;

-- Delete all records from required tables
DELETE FROM PeliculaActor;
DELETE FROM Pelicula;
DELETE FROM Actor;
DELETE FROM Genero;

BEGIN 
-- Insert new genero
INSERT INTO Genero (nombre_genero) VALUES ('Actionxyz');
DECLARE @generoId INT;
SET @generoId = SCOPE_IDENTITY();

-- Insert new actors
INSERT INTO Actor (nombre_actor, apellidos_actor) VALUES ('Keanu', 'Reeves');

DECLARE @actor1_id INT = SCOPE_IDENTITY();

INSERT INTO Actor (nombre_actor, apellidos_actor) VALUES ('Laurence', 'Fishburne');
DECLARE @actor2_id INT = SCOPE_IDENTITY();

INSERT INTO Pelicula (titulo,subtitulada,estreno, genero_id)
 	VALUES('Matrix',CAST(1 AS BIT), CAST(0 AS BIT), @generoId);
 	
DECLARE @pelicula_id INT = SCOPE_IDENTITY();
 	
INSERT INTO PeliculaActor (pelicula_id,actor_id)
 	VALUES
 	(@pelicula_id, @actor1_id),
 	(@pelicula_id, @actor2_id);

END
GO