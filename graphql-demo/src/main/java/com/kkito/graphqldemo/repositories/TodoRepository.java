package com.kkito.graphqldemo.repositories;

import com.kkito.graphqldemo.models.Todo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {}
