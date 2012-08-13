package org.bm.cookbook.db.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-13T03:05:53.653+0200")
@StaticMetamodel(Recipe.class)
public class Recipe_ {
	public static volatile SingularAttribute<Recipe, Integer> oid;
	public static volatile SingularAttribute<Recipe, Date> creationDate;
	public static volatile SingularAttribute<Recipe, String> preheat;
	public static volatile SingularAttribute<Recipe, String> title;
	public static volatile SingularAttribute<Recipe, Date> updatingDate;
	public static volatile SingularAttribute<Recipe, Integer> version;
	public static volatile ListAttribute<Recipe, Ingredient> ingredients;
	public static volatile SingularAttribute<Recipe, Cookbook> cookbook;
	public static volatile ListAttribute<Recipe, Step> steps;
}
