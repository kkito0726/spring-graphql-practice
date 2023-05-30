package com.kkito.graphqldemo.controllers;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = HealthController.BASE_URL)
public class HealthController {

  public static final String BASE_URL = "/health";

  @GetMapping
  public ResponseEntity<String> getHealth() {
    return ResponseEntity.ok("API is running");
  }

  @GetMapping("/graphql")
  public ResponseEntity<String> helloGraphql() {
    String schema = "type Query{hello: String}";

    // 定義したスキーマを読み込む
    SchemaParser schemaParser = new SchemaParser();
    TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

    // Node.jsでやったときのResolver的な処理だと思われる
    RuntimeWiring runtimeWiring = newRuntimeWiring()
      .type(
        "Query",
        builder -> builder.dataFetcher("hello", new StaticDataFetcher("world"))
      )
      .build();

    // TypeDefsとResolverを読み込んでgraphQLスキーマを生成
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(
      typeDefinitionRegistry,
      runtimeWiring
    );

    // GraphQLの実行
    GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
    ExecutionResult executionResult = build.execute("{hello}");

    return ResponseEntity.ok(executionResult.getData().toString());
  }
}
