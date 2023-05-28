package com.kkito.graphqldemo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String title;
  private boolean isCompleted;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;
}
