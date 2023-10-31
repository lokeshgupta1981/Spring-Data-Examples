package com.howtodoinjava.audit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity

/*@Audited*/
/*@EntityListeners(AuditingEntityListener.class)*/
@Slf4j
public class Employee {

  public Employee(String name){
    this.name = name;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  /*@CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;*/

  /*@CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;*/

  private LocalDateTime createdOn;

  private LocalDateTime updatedOn;

  @PrePersist
  protected void onCreate() {
    log.info("onCreate() called");
    createdOn = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    log.info("onUpdate() called");
    updatedOn = LocalDateTime.now();
  }
}
