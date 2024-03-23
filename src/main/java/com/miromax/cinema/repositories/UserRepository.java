package com.miromax.cinema.repositories;

import com.miromax.cinema.entities.User;
import com.miromax.cinema.entities.enums.UserRole;
import com.miromax.cinema.entities.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByRoleIs(UserRole role, Pageable pageable);
    Page<User> findByStatusIs(UserStatus status, Pageable pageable);
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
            "LOWER(u.phone) LIKE LOWER(CONCAT('%', :value, '%'))")
    Page<User> searchUsers(@Param("value") String value, Pageable pageable);
}
