package com.example.backend.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.backend.service.BorrowService;
import com.example.backend.web.dto.BorrowRecordDto;
import com.example.backend.web.dto.BorrowRequest;
import com.example.backend.web.dto.ReturnRequest;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class BorrowController {
    
    @Autowired
    private BorrowService borrowService;


    @PostMapping("/borrow")
    public BorrowRecordDto borrow_book(@Valid @RequestBody BorrowRequest req) {
        return borrowService.borrowBook(req.userId(), req.bookId());
    }
    
    
    @PostMapping("/return")
    public BorrowRecordDto return_book(@Valid @RequestBody ReturnRequest req) {
        return borrowService.returnBook(req.recordId());
    }


    @GetMapping("/borrows")
    public List<BorrowRecordDto> getBorrowHistory(@RequestParam Long userId) {
        return borrowService.listBorrowHistory(userId);
    }
}
