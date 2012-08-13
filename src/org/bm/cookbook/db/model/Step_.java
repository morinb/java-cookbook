package org.bm.cookbook.db.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-13T03:05:53.654+0200")
@StaticMetamodel(Step.class)
public class Step_ {
	public static volatile SingularAttribute<Step, StepPK> id;
	public static volatile SingularAttribute<Step, Date> creationDate;
	public static volatile SingularAttribute<Step, String> text;
	public static volatile SingularAttribute<Step, Date> updatingDate;
	public static volatile SingularAttribute<Step, Integer> version;
	public static volatile SingularAttribute<Step, Recipe> recipe;
}
