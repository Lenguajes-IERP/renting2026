USE [videorent] -- TODO CAMBIAN POR SU B.D.


-- Usamos un bloque TRY para manejar errores de forma segura

    -- 1. Limpieza de datos (Orden de hijo a padre para evitar errores de FK)
    DELETE FROM PeliculaActor;
    DELETE FROM Actor;
    DELETE FROM Pelicula;
    DELETE FROM Genero;

    -- Opcional: Resetear los contadores de identidad a 0
    DBCC CHECKIDENT ('Actor', RESEED, 0);
    DBCC CHECKIDENT ('Pelicula', RESEED, 0);
    DBCC CHECKIDENT ('Genero', RESEED, 0);

    -- 2. Insertar en Genero
    SET IDENTITY_INSERT Genero ON;
    INSERT INTO Genero (genero_id, nombre_genero) VALUES
    (1, 'Action'), (2, 'Comedy'), (3, 'Drama'), (4, 'Sci-Fi'), (5, 'Thriller'),
    (6, 'Horror'), (7, 'Animation'), (8, 'Adventure'), (9, 'Romance'), (10, 'Crime');
    SET IDENTITY_INSERT Genero OFF;

    -- 3. Insertar en Actor
    SET IDENTITY_INSERT Actor ON;
    INSERT INTO Actor (actor_id, nombre_actor, apellidos_actor) VALUES
    (1, 'Tom', 'Holland'), (2, 'Zendaya', 'Coleman'), (3, 'Timothée', 'Chalamet'),
    (4, 'Florence', 'Pugh'), (5, 'Leonardo', 'DiCaprio'), (6, 'Margot', 'Robbie'),
    (7, 'Ryan', 'Gosling'), (8, 'Emma', 'Stone'), (9, 'Joaquin', 'Phoenix'),
    (10, 'Scarlett', 'Johansson'), (11, 'Chris', 'Hemsworth'), (12, 'Natalie', 'Portman'),
    (13, 'Keanu', 'Reeves'), (14, 'Charlize', 'Theron'), (15, 'Dwayne', 'Johnson'),
    (16, 'Gal', 'Gadot'), (17, 'Idris', 'Elba'), (18, 'Emily', 'Blunt'),
    (19, 'Daniel', 'Craig'), (20, 'Ana', 'de Armas'), (21, 'Robert', 'Pattinson'),
    (22, 'Zoë', 'Kravitz'), (23, 'Austin', 'Butler'), (24, 'Cate', 'Blanchett'),
    (25, 'Paul', 'Dano'), (26, 'Anya', 'Taylor-Joy'), (27, 'Pedro', 'Pascal'),
    (28, 'Bella', 'Ramsey'), (29, 'Millie', 'Bobby Brown'), (30, 'Henry', 'Cavill');
    SET IDENTITY_INSERT Actor OFF;

    -- 4. Insertar en Pelicula
    SET IDENTITY_INSERT Pelicula ON;
    INSERT INTO Pelicula (pelicula_id, titulo, subtitulada, estreno, genero_id) VALUES
    (1, 'Spider-Man: No Way Home', 0, 1, 1), (2, 'Dune', 0, 1, 4), (3, 'Once Upon a Time in Hollywood', 0, 0, 3),
    (4, 'Little Women', 0, 0, 3), (5, 'The French Dispatch', 1, 1, 2), (6, 'Barbie', 0, 1, 2),
    (7, 'Oppenheimer', 0, 1, 3), (8, 'Poor Things', 0, 1, 2), (9, 'Joker', 0, 0, 10),
    (10, 'Marriage Story', 0, 0, 3), (11, 'Thor: Love and Thunder', 0, 1, 1), (12, 'Black Swan', 0, 0, 5),
    (13, 'John Wick: Chapter 4', 0, 1, 1), (14, 'Mad Max: Fury Road', 0, 0, 1), (15, 'Jumanji: The Next Level', 0, 0, 8),
    (16, 'Wonder Woman 1984', 0, 0, 1), (17, 'The Suicide Squad', 0, 1, 1), (18, 'A Quiet Place Part II', 0, 1, 6),
    (19, 'No Time to Die', 0, 1, 1), (20, 'Knives Out', 0, 0, 10), (21, 'The Batman', 0, 1, 10),
    (22, 'The Northman', 0, 1, 8), (23, 'Elvis', 0, 1, 3), (24, 'Tár', 0, 1, 3),
    (25, 'The Fabelmans', 0, 1, 3), (26, 'The Witch', 0, 0, 6), (27, 'The Last of Us (TV Series)', 0, 1, 3),
    (28, 'The Whale', 0, 1, 3), (29, 'Enola Holmes', 0, 1, 8), (30, 'Argylle',0,1,1),
    (31, 'Avatar: The Way of Water',0,1,4), (32, 'Guardians of the Galaxy Vol. 3',0,1,1),
    (33, 'The Super Mario Bros. Movie',0,1,7), (34, 'Fast X',0,1,1), (35, 'Transformers: Rise of the Beasts',0,1,1),
    (36, 'Spider-Man: Across the Spider-Verse',0,1,7), (37, 'Elemental',0,1,7), (38, 'Mission: Impossible – Dead Reckoning Part One',0,1,1),
    (39, 'Teenage Mutant Ninja Turtles: Mutant Mayhem',0,1,7), (40, 'Gran Turismo',0,1,1), (41, 'The Creator',0,1,4),
    (42, 'Killers of the Flower Moon',0,1,10), (43, 'Napoleon',0,1,3), (44, 'Wonka',0,1,8),
    (45, 'Aquaman and the Lost Kingdom',0,1,1), (46, 'Anyone But You',0,1,9), (47, 'Mean Girls',0,1,2),
    (48, 'Damsel',0,1,8), (49, 'Madame Web',0,1,1), (50, 'Drive-Away Dolls',0,1,2);
    SET IDENTITY_INSERT Pelicula OFF;

    -- 5. Insertar en PeliculaActor (No es Identity, no requiere SET IDENTITY_INSERT)
    INSERT INTO PeliculaActor (pelicula_id, actor_id) VALUES
    (1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6), (4, 4), (4, 3), (5, 3), (5, 8),
    (6, 6), (6, 7), (7, 4), (7, 24), (8, 8), (8, 25), (9, 9), (9, 25), (10, 10), (10, 9),
    (11, 11), (11, 12), (12, 12), (13, 13), (14, 14), (15, 15), (16, 16), (17, 17),
    (18, 18), (19, 19), (20, 20), (21, 21), (22, 22), (23, 23), (24, 24), (25, 25),
    (26, 26), (27, 27), (27, 28), (28, 27), (29, 29), (30, 30), (31, 24), (32, 11),
    (33, 27), (34, 15), (35, 17), (36, 1), (37, 2), (38, 19), (39, 22), (40, 23),
    (41, 18), (42, 5), (43, 9), (44, 3), (45, 16), (46, 2), (47, 6), (48, 29), (49, 10), (50, 26);

    

    -- Si hay error, deshacemos todo y mostramos el mensaje
   --  IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION;
    
    -- Aseguramos que los IDENTITY_INSERT queden en OFF aunque falle
    SET IDENTITY_INSERT Genero OFF;
    SET IDENTITY_INSERT Actor OFF;
    SET IDENTITY_INSERT Pelicula OFF;

    -- DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
    -- PRINT 'ERROR: ' + @ErrorMessage;