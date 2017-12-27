package org.coder229.datahub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    public String category;

    @Column
    public String code;

    @Column
    public String value;

    @Column
    public String units;

    @Column
    public ZonedDateTime effective;

    @ManyToOne
    @JoinColumn
    public Source source;
}
