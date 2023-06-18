package com.kkito.graphqldemo;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.kkito.graphqldemo.dataFetchers.GraphQLDataFetchers;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GraphQLProvider {

  private GraphQL graphQL;

  @Autowired
  private GraphQLDataFetchers graphQLDataFetchers;

  @Bean
  public GraphQL graphQL() {
    return graphQL;
  }

  @PostConstruct
  public void init() throws IOException {
    URL url = Resources.getResource("/graphql/schema.graphqls");
    System.out.println(url);
    String sdl = Resources.toString(url, Charsets.UTF_8);
    GraphQLSchema graphQLSchema = buildSchema(sdl);
    this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  // typeDefsとruntimeWiringをあわせてGraphQLSchemaを作る
  private GraphQLSchema buildSchema(String sdl) {
    SchemaParser schemaParser = new SchemaParser();
    TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(sdl);

    RuntimeWiring runtimeWiring = buildWiring();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(
      typeDefinitionRegistry,
      runtimeWiring
    );
  }

  // runtimeWiringを作る
  private RuntimeWiring buildWiring() {
    return RuntimeWiring
      .newRuntimeWiring()
      .type(
        TypeRuntimeWiring
          .newTypeWiring("Query")
          .dataFetcher("getTodos", graphQLDataFetchers.getTodosDataFetcher())
      )
      .type(
        TypeRuntimeWiring
          .newTypeWiring("Query")
          .dataFetcher(
            "getTodoById",
            graphQLDataFetchers.getTodoByIDataFetcher()
          )
      )
      .build();
  }
}
