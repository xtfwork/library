package com.example.backend.web;

import org.springframework.web.bind.annotation.RestController;

import com.example.backend.repo.BookRepository;

import java.util.List;

import com.example.backend.domain.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> list(@RequestParam(value = "q", required = false) String q){
        if(q == null || q.isBlank()){
            return bookRepository.findAll();
        } else {
            return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q);
        }
    }

}
