package org.bm.cookbook.db.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2012-08-15T18:23:41.315+0200")
@StaticMetamodel(Ingredient.class)
public class Ingredient_ {
	public static volatile SingularAttribute<Ingredient, Integer> oid;
	public static volatile SingularAttribute<Ingredient, Date> creationDate;
	public static volatile SingularAttribute<Ingredient, String> name;
	public static volatile SingularAttribute<Ingredient, Date> updatingDate;
	public static volatile SingularAttribute<Ingredient, Integer> version;
	public static volatile SingularAttribute<Ingredient, Image> image;
}
