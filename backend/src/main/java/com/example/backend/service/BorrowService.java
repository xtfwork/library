package com.example.backend.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.repo.BookRepository;
import com.example.backend.repo.BorrowRecordRepository;
import com.example.backend.repo.UserRepository;
import com.example.backend.support.ConflictException;
import com.example.backend.support.NotFoundException;
import com.example.backend.web.dto.BorrowRecordDto;

import jakarta.transaction.Transactional;

import com.example.backend.domain.User;
import com.example.backend.domain.Book;
import com.example.backend.domain.BorrowRecord;

@Service
public class BorrowService {
    
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private BorrowRecordRepository borrowRepo;


    @Transactional
    public BorrowRecordDto borrowBook(Long userId, Long bookId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new NotFoundException("user not found: " + userId));
        Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new NotFoundException("book not found: " + bookId));


        if (!book.getIsAvailable()){
            throw new ConflictException("book not available");
        }
        borrowRepo.findByUserIdAndBookIdAndAndReturnAtIsNull(userId, bookId)
            .ifPresent(r -> { throw new ConflictException("already borrowed"); });

        
        book.setIsAvailable(false);
        bookRepo.save(book);


        BorrowRecord record = BorrowRecord.builder()
            .user(user)
            .book(book)
            .borrowAt(Instant.now())
            .build();
        record = borrowRepo.save(record);


        return new BorrowRecordDto(record.getId(), user.getId(), book.getId(),record.getBorrowAt(), record.getReturnAt());
    }


    @Transactional
    public BorrowRecordDto returnBook(Long recordId){
        BorrowRecord record = borrowRepo.findById(recordId)
            .orElseThrow(() -> new NotFoundException("borrow record not found: " + recordId));
        
            
        if (record.getReturnAt() != null){
            throw new ConflictException("already returned");
        }


        record.setReturnAt(Instant.now());
        borrowRepo.save(record);


        Book book = record.getBook();
        book.setIsAvailable(true);
        bookRepo.save(book);


        return new BorrowRecordDto(
            record.getId(), record.getUser().getId(), book.getId(), record.getBorrowAt(), record.getReturnAt()
        );
    }


    public List<BorrowRecordDto> listBorrowHistory(Long userId) {
        return borrowRepo.findByUserId(userId).stream()
            .map(r -> new BorrowRecordDto(
                r.getId(),
                r.getUser().getId(),
                r.getBook().getId(),
                r.getBorrowAt(),
                r.getReturnAt()
            )).toList();
    }
}
