package com.example.backend.web.dto;

import java.time.Instant;

public record BorrowRecordDto(

    Long id,
    Long userId,
    Long bookId,
    Instant borrowAt,
    Instant returnAt

) 
{}