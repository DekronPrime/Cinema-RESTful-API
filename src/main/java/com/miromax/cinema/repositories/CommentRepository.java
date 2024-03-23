package com.miromax.cinema.repositories;

import com.miromax.cinema.entities.Comment;
import com.miromax.cinema.entities.enums.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByMovieId(Long movieId, Pageable pageable);
    Page<Comment> findByUserId(Long userId, Pageable pageable);
    Page<Comment> findByStatusIs(CommentStatus status, Pageable pageable);
    @Query("SELECT c FROM Comment c WHERE " +
            "LOWER(c.content) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
            "c.likes = :valueNumeric OR " +
            "c.dislikes = :valueNumeric")
    Page<Comment> searchComments(
            @Param("value") String value,
            @Param("valueNumeric") Integer valueNumeric,
            Pageable pageable);
}
