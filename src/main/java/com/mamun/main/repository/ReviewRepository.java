package com.mamun.main.repository;


import com.mamun.main.entity.BookEntity;
import com.mamun.main.entity.ReviewEntity;
import com.mamun.main.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByBookId(Long bookId);

}

