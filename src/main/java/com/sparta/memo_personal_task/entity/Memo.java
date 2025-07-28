package com.sparta.memo_personal_task.entity;

import com.sparta.memo_personal_task.dto.MemoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Memo {
    private Long id;
    private String username;
    private String title;
    private String contents;

    public Memo(String title, String contents, String username) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contents, String username){
        this.username = username;
        this.title = title;
        this.contents = contents;

    }

}
