INSERT INTO unit(unit_db_id, abbreviation, name) VALUES(NEXT VALUE FOR unit_db_id, 'g', 'gramme');

INSERT INTO unit(unit_db_id, abbreviation, name) VALUES(NEXT VALUE FOR unit_db_id, 'L', 'litre');

INSERT INTO unit(unit_db_id, abbreviation, name) VALUES(NEXT VALUE FOR unit_db_id, 'oz', 'oz');

INSERT INTO unit(unit_db_id, abbreviation, name) VALUES(NEXT VALUE FOR unit_db_id, '', '');



INSERT INTO  ingredient (ingredient_db_id, name, image_db_id) VALUES (NEXT VALUE FOR ingredient_db_id, 'FRAISE', null);

INSERT INTO  ingredient (ingredient_db_id, name, image_db_id) VALUES (NEXT VALUE FOR ingredient_db_id, 'POMME', null);

INSERT INTO  ingredient (ingredient_db_id, name, image_db_id) VALUES (NEXT VALUE FOR ingredient_db_id, 'FARINE', null);

INSERT INTO  ingredient (ingredient_db_id, name, image_db_id) VALUES (NEXT VALUE FOR ingredient_db_id, 'OEUF', null);



INSERT INTO cookbook (cookbook_db_id, name, image_db_id) VALUES (NEXT VALUE FOR cookbook_db_id, 'Gateau', null);

commit;
