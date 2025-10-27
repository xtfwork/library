package com.example.backend.web.dto;

import jakarta.validation.constraints.NotNull;

public record ReturnRequest(

    @NotNull Long recordId

) {}
