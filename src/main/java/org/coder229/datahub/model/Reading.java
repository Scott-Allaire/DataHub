package org.coder229.datahub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Entity
public class Reading {
    @Id
    @GeneratedValue
    public Long id;

    @Column
    @Enumerated(EnumType.STRING)
    public ReadingType type;

    @Column
    public String value;

    @Column
    public String units;

    @Column
    public ZonedDateTime dateTime;

    @ManyToOne
    @JoinColumn
    public Source source;
}
