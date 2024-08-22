package com.example.stage.dao;

import com.example.stage.entity.Parts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartRepository extends JpaRepository<Parts, Long> {


}