package com.example.backend.repo;

import com.example.backend.domain.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    List<BorrowRecord> findByUserId(Long userId);
    List<BorrowRecord> findByBookId(Long bookId);
    List<BorrowRecord> findByBorrowAtIsNull();

    Optional<BorrowRecord> findByUserIdAndBookIdAndAndReturnAtIsNull(long userId, long bookId);
    
}
