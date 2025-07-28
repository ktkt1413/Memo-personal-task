package com.sparta.memo_personal_task.service;

import com.sparta.memo_personal_task.dto.MemoRequestDto;
import com.sparta.memo_personal_task.dto.MemoResponseDto;
import com.sparta.memo_personal_task.entity.Memo;
import com.sparta.memo_personal_task.repository.MemoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository repository;

    public MemoServiceImpl (MemoRepository repository) {
        this.repository = repository;
    }


    @Override
    public MemoResponseDto saveMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername());
        return repository.saveMemo(memo);
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {
        Memo memo = repository.findMemoByIdOrElseThrow(id);
        return new MemoResponseDto(memo);
    }

//    @Override
//    public MemoResponseDto findMemoByIdOrElseThrow(Long id) {
//        return null;
//    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        return repository.findAllMemos();
    }

    @Override
    public List<MemoResponseDto> getMemosByKeyword(String Keyword) {

        return repository.getMemosByKeyword(Keyword);
    }

    @Override
    public List<MemoResponseDto> findMemosByUsername(String username) {
        return repository.findMemosByUsername(username);
    }

    @Transactional
    @Override
    public MemoResponseDto updateMemo(Long id, String title, String contents) {
        if(title == null || contents == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목과 내용을 모두 입력해주세요");
        }
        int updateRow = repository.updateMemo(id, title, contents);
        if(updateRow ==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "업데이트되지 않았습니다.");
        }
        Memo memo = repository.findMemoByIdOrElseThrow(id);

        return new MemoResponseDto(memo);
    }

    @Override
    public void deleteMemoById(Long id) {
        int deleteRow = repository.deleteMemoById(id);
        if(deleteRow ==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 메모가 존재하지 않습니다.");
        }
    }
}
