package com.sparta.memo_personal_task.service;

import com.sparta.memo_personal_task.dto.MemoRequestDto;
import com.sparta.memo_personal_task.dto.MemoResponseDto;

import java.util.List;

public interface MemoService {
    MemoResponseDto saveMemo(MemoRequestDto memoRequestDto);
    MemoResponseDto findMemoById(Long id);
//    MemoResponseDto findMemoByIdOrElseThrow(Long id);
    List<MemoResponseDto> findAllMemos();
    List<MemoResponseDto> getMemosByKeyword(String Keyword);
    List<MemoResponseDto> findMemosByUsername(String username);
    MemoResponseDto updateMemo(Long id, String title, String contents);
    void deleteMemoById(Long id);

}
