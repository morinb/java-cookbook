package org.bm.cookbook.db.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2012-08-15T18:23:41.309+0200")
@StaticMetamodel(Image.class)
public class Image_ {
	public static volatile SingularAttribute<Image, Integer> oid;
	public static volatile SingularAttribute<Image, Date> creationDate;
	public static volatile SingularAttribute<Image, String> imagePath;
	public static volatile SingularAttribute<Image, String> name;
	public static volatile SingularAttribute<Image, Date> updatingDate;
	public static volatile SingularAttribute<Image, Integer> version;
}
