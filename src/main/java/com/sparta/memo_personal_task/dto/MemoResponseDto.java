package com.sparta.memo_personal_task.dto;

import com.sparta.memo_personal_task.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class MemoResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;


    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.title = memo.getTitle();
    }
}
