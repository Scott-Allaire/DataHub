package org.coder229.datahub.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ {
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, Boolean> enabled;
}
