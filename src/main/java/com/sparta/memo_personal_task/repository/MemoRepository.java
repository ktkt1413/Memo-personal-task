package com.sparta.memo_personal_task.repository;

import com.sparta.memo_personal_task.dto.MemoResponseDto;
import com.sparta.memo_personal_task.entity.Memo;

import java.util.List;


public interface MemoRepository {
    MemoResponseDto saveMemo(Memo memo);

    List<MemoResponseDto> findAllMemos();

    List<MemoResponseDto> getMemosByKeyword(String keyword);

    List<MemoResponseDto> findMemosByUsername(String username);

    Memo findMemoByIdOrElseThrow(Long id);

    int updateMemo(Long id, String title, String contents);

    int deleteMemoById(Long id);
}
