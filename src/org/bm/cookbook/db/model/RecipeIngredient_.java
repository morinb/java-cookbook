package org.bm.cookbook.db.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-15T19:10:55.584+0200")
@StaticMetamodel(RecipeIngredient.class)
public class RecipeIngredient_ {
	public static volatile SingularAttribute<RecipeIngredient, Integer> oid;
	public static volatile SingularAttribute<RecipeIngredient, Date> creationDate;
	public static volatile SingularAttribute<RecipeIngredient, Date> updatingDate;
	public static volatile SingularAttribute<RecipeIngredient, Integer> version;
	public static volatile SingularAttribute<RecipeIngredient, Ingredient> ingredient;
	public static volatile SingularAttribute<RecipeIngredient, Unit> unit;
	public static volatile SingularAttribute<RecipeIngredient, Recipe> recipe;
	public static volatile SingularAttribute<RecipeIngredient, Integer> quantity;
}
