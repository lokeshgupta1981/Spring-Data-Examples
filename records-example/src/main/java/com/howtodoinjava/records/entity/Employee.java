package com.howtodoinjava.records.entity;

import com.howtodoinjava.records.dto.EmployeeRecord;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@SqlResultSetMapping(
    name = "EmployeeRecordMapping",
    classes = @ConstructorResult(
        targetClass = EmployeeRecord.class,
        columns = {
            @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "name", type = String.class),
            @ColumnResult(name = "salary", type = Long.class)
        }
    )
)
@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Long salary;
}