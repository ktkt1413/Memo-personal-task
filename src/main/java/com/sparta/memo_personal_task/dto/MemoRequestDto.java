package com.sparta.memo_personal_task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemoRequestDto {
    private String username;
    private String title;
    private String contents;

}