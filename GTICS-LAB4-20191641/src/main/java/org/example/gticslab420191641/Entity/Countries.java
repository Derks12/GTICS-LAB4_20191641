package org.example.gticslab420191641.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "countries")
public class Countries {
    @Id
    private String country_id;

    @Column(name = "country_name")
    private String countryName;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;


}