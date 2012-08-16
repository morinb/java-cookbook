package org.bm.cookbook.db.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2012-08-15T18:23:41.286+0200")
@StaticMetamodel(Cookbook.class)
public class Cookbook_ {
	public static volatile SingularAttribute<Cookbook, Integer> oid;
	public static volatile SingularAttribute<Cookbook, Date> creationDate;
	public static volatile SingularAttribute<Cookbook, String> name;
	public static volatile SingularAttribute<Cookbook, Date> updatingDate;
	public static volatile SingularAttribute<Cookbook, Integer> version;
	public static volatile SingularAttribute<Cookbook, Image> image;
	public static volatile ListAttribute<Cookbook, Recipe> recipes;
}
