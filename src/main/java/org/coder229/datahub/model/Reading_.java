package org.coder229.datahub.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;

@StaticMetamodel(Reading.class)
public class Reading_ {
    public static volatile SingularAttribute<Reading, String> category;
    public static volatile SingularAttribute<Reading, String> code;
    public static volatile SingularAttribute<Reading, String> value;
    public static volatile SingularAttribute<Reading, String> units;
    public static volatile SingularAttribute<Reading, ZonedDateTime> effective;
    public static volatile SingularAttribute<Reading, Source> source;
}

