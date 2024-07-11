package com.project.shelf.sub_types;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTypesRepository extends JpaRepository<SubTypes, Integer> {
}
