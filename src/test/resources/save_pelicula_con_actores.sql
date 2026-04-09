USE videorent;

-- Delete all records from required tables
DELETE FROM PeliculaActor;
DELETE FROM Pelicula;
DELETE FROM Actor;
DELETE FROM Genero;
-- Insert new genero
INSERT INTO Genero (nombre_genero) VALUES ('Action');

-- Capture generated genero_id
DECLARE @genero_id INT = SCOPE_IDENTITY();

-- Insert new actors
INSERT INTO Actor (nombre_actor, apellidos_actor) VALUES ('Keanu', 'Reeves');
DECLARE @actor1_id INT = SCOPE_IDENTITY();

INSERT INTO Actor (nombre_actor, apellidos_actor) VALUES ('Laurence', 'Fishburne');
DECLARE @actor2_id INT = SCOPE_IDENTITY();