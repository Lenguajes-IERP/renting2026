USE videorent;
-- Delete all records from required tables
DELETE FROM PeliculaActor;
DELETE FROM Pelicula;
DELETE FROM Actor;
DELETE FROM Genero;
-- Insert new genero
-- Insert data into Genero table
INSERT INTO Genero (nombre_genero) VALUES
('Action'),
('Comedy'),
('Drama'),
('Sci-Fi'),
('Thriller'),
('Horror'),
('Animation'),
('Adventure'),
('Romance'),
('Crime');