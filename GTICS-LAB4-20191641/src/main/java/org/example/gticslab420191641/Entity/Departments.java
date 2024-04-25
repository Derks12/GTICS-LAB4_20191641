package org.example.gticslab420191641.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employees manager;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}