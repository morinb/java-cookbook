INSERT INTO unit(unit_db_id, abbreviation, name) VALUES(NEXT VALUE FOR unit_db_id, 'g', 'gramme');

INSERT INTO unit(unit_db_id, abbreviation, name) VALUES(NEXT VALUE FOR unit_db_id, 'L', 'litre');

INSERT INTO unit(unit_db_id, abbreviation, name) VALUES(NEXT VALUE FOR unit_db_id, 'oz', 'oz');

INSERT INTO unit(unit_db_id, abbreviation, name) VALUES(NEXT VALUE FOR unit_db_id, '', '');



INSERT INTO  ingredient (ingredient_db_id, name, image_db_id) VALUES (NEXT VALUE FOR ingredient_db_id, 'FRAISE', null);

INSERT INTO  ingredient (ingredient_db_id, name, image_db_id) VALUES (NEXT VALUE FOR ingredient_db_id, 'POMME', null);

INSERT INTO  ingredient (ingredient_db_id, name, image_db_id) VALUES (NEXT VALUE FOR ingredient_db_id, 'FARINE', null);

INSERT INTO  ingredient (ingredient_db_id, name, image_db_id) VALUES (NEXT VALUE FOR ingredient_db_id, 'OEUF', null);



INSERT INTO cookbook (cookbook_db_id, name, image_db_id) VALUES (NEXT VALUE FOR cookbook_db_id, 'Gateau', null);

INSERT INTO cookbook_ingredient(cookbook_db_id, ingredient_db_id, quantity, unit_db_id) VALUES ((select cookbook_db_id from cookbook where name = 'Gateau'), (select ingredient_db_id from ingredient where name = 'FARINE'), '500', (select unit_db_id from unit where abbreviation = 'g'));

INSERT INTO cookbook_ingredient(cookbook_db_id, ingredient_db_id, quantity, unit_db_id) VALUES ((select cookbook_db_id from cookbook where name = 'Gateau'), (select ingredient_db_id from ingredient where name = 'OEUF'), '4', (select unit_db_id from unit where abbreviation = ''));

commit;
