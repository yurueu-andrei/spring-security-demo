package com.example.demo.repository;

import com.example.demo.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProgramRepository extends JpaRepository<Program, Long> {

}
