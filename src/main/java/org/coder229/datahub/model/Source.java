package org.coder229.datahub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Source {
    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public String name;
}
