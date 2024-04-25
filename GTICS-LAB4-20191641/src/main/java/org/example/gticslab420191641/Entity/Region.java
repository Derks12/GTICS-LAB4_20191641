package org.example.gticslab420191641.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "regions")
public class Region {

    @Id
    private float regionId;
    @Column(name = "region_name")
    private String regionName;

}