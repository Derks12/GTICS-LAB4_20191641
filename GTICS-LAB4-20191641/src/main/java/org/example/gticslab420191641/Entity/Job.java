package org.example.gticslab420191641.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @Column(name = "job_id", nullable = false)
    private String jobId;
    @Column(name = "job_title", nullable = false)
    private String jobTitle;
    @Column(name = "min_salary")
    private Integer minSalary;
    @Column(name = "max_salary")
    private Integer maxSalary;

}
