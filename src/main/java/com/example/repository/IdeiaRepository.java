package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Ideia;

@Repository
public interface IdeiaRepository extends JpaRepository<Ideia, Integer> {

}
