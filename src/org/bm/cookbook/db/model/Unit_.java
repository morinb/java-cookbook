package org.bm.cookbook.db.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-15T18:23:41.388+0200")
@StaticMetamodel(Unit.class)
public class Unit_ {
	public static volatile SingularAttribute<Unit, Integer> oid;
	public static volatile SingularAttribute<Unit, String> abbreviation;
	public static volatile SingularAttribute<Unit, Date> creationDate;
	public static volatile SingularAttribute<Unit, String> name;
	public static volatile SingularAttribute<Unit, Date> updatingDate;
	public static volatile SingularAttribute<Unit, Integer> version;
}
