-- Delete all
DROP TABLE recipe_step IF EXISTS;
DROP TABLE ingredient IF EXISTS;
DROP TABLE recipe IF EXISTS;
DROP TABLE cookbook IF EXISTS;
DROP TABLE image IF EXISTS;
DROP TABLE unit IF EXISTS;

DROP SEQUENCE cookbook_db_id IF EXISTS;
DROP SEQUENCE ingredient_db_id IF EXISTS;
DROP SEQUENCE image_db_id IF EXISTS;
DROP SEQUENCE unit_db_id IF EXISTS;
DROP SEQUENCE recipe_db_id IF EXISTS;
DROP SEQUENCE recipe_step_db_id IF EXISTS;