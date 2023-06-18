package com.kkito.graphqldemo.dataFetchers;

import com.kkito.graphqldemo.models.Todo;
import graphql.schema.DataFetcher;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

  private static List<Todo> todos = Arrays.asList(
    new Todo(
      UUID.randomUUID(),
      "todo1",
      false,
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
    new Todo(
      UUID.randomUUID(),
      "todo2",
      false,
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
    new Todo(
      UUID.randomUUID(),
      "todo3",
      false,
      LocalDateTime.now(),
      LocalDateTime.now()
    )
  );

  public DataFetcher getTodosDataFetcher() {
    return dataFetchingEnviroment -> {
      List<Todo> todos = dataFetchingEnviroment.getSource();
      return todos;
    };
  }

  public DataFetcher getTodoByIDataFetcher() {
    return dataFetchingEnvironment -> {
      List<Todo> todos = dataFetchingEnvironment.getSource();
      String todoId = dataFetchingEnvironment.getArgument("id");
      return todos
        .stream()
        .filter(todo -> todo.getId().equals(todoId))
        .findFirst()
        .orElse(null);
    };
  }
}
