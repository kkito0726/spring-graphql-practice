package com.kkito.graphqldemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTodoDto {

  private String title;
  private boolean completed;

  private boolean getCompleted() {
    return completed;
  }
}
