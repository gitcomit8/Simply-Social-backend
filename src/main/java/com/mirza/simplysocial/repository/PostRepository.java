package com.mirza.simplysocial.repository;

import com.mirza.simplysocial.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //TODO: Add custom queries
}
