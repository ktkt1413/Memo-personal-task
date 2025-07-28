package com.sparta.memo_personal_task.controller;


import com.sparta.memo_personal_task.dto.MemoRequestDto;
import com.sparta.memo_personal_task.dto.MemoResponseDto;
import com.sparta.memo_personal_task.entity.Memo;
import com.sparta.memo_personal_task.service.MemoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MemoController {

    private final MemoServiceImpl memoService;

    public MemoController(MemoServiceImpl memoService) {
        this.memoService = memoService;
    }

    @PostMapping("/memos")
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto requestDto) {

        return new ResponseEntity<>(memoService.saveMemo(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/memos/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id) {

        return new ResponseEntity<>(memoService.findMemoById(id), HttpStatus.OK);
    }

    @GetMapping("/memos")
    public ResponseEntity<List<MemoResponseDto>> findAllMemos() {
        return new ResponseEntity<>(memoService.findAllMemos(), HttpStatus.OK);
    }

    @GetMapping("/memos/contents")
    public List<MemoResponseDto> getMemosByKeyword(@RequestParam String keyword) {
        return memoService.getMemosByKeyword(keyword);
    }

    @GetMapping("/memos/username")
    public List<MemoResponseDto> findMemoByUsername(@RequestParam String username) {
        return memoService.findMemosByUsername(username);
    }

    @PutMapping("/memos/{id}")
    public ResponseEntity<MemoResponseDto> updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        return new ResponseEntity<>(memoService.updateMemo(id, requestDto.getTitle(), requestDto.getContents() ), HttpStatus.OK);
    }

    @DeleteMapping("/memos/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id){
        memoService.deleteMemoById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
