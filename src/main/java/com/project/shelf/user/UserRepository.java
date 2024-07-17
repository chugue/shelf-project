package com.project.shelf.user;

import com.project.shelf.user.UserResponseRecord.MonthlyUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
            SELECT new com.project.shelf.user.UserResponseRecord.MonthlyUserDTO(YEAR(u.createdAt), MONTH(u.createdAt), COUNT(u))
            FROM User u
            GROUP BY YEAR(u.createdAt), MONTH(u.createdAt)
            ORDER BY YEAR(u.createdAt), MONTH(u.createdAt)
           """)
    List<MonthlyUserDTO> findUserByMonth();

    Optional<User> findByEmail(@Param("email") String email);

}
