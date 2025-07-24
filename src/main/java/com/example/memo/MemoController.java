package com.example.memo;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/memos")
public class MemoController {


    private final Map<Long, Memo> memoList = new HashMap<>();


    @PostMapping       // ResponseEntity<>를 반환 형태로 정했다면 다른 메서드도 같은 타입으로 반환한다.
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) {

        //식별자가 1씩 증가하도록 만듦
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());

        // Inmemory DB에 Memo 메모
        memoList.put(memoId,  memo);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> findAllMemos() {
        //init List
        List<MemoResponseDto> responseList = new ArrayList<>();

        //HashMap<Memo> -> List<MemoResponseDto>
        for(Memo memo : memoList.values()) {
            MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
            responseList.add(memoResponseDto);
        }

        // Map To List                                    new MemoResponseDto(...)의 람다식
        // responseList = memoList.values().stream().map(MemoResponseDto::new).toList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> finmMemoById(@PathVariable Long id){
        Memo memo = memoList.get(id);
        if(memo == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(new MemoResponseDto(memo),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemoById(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        Memo memo = memoList.get(id);

        if(memo == null){
            return ResponseEntity.notFound().build();
        }
        // 필수값 검증
        if(dto.getTitle() == null || dto.getContents() == null){
            return ResponseEntity.badRequest().body(new MemoResponseDto(memo));
                                             // 상태코드만 주면 왜 bad request가 발생한지 모르므로 body에 관련 정보를 담아줌
        }

        // 메모 수정
        memo.update(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        Memo memo = memoList.get(id);
        // 찾는 memo가 없으면 404에러
        if(memo == null){
            return ResponseEntity.notFound().build();
        }
        // 수정될 제목이 없으면 400에러
        if(dto.getTitle() == null){
            return ResponseEntity.badRequest().body(new MemoResponseDto(memo));
        }

        memo.updateTitle(dto);

        return new ResponseEntity(new MemoResponseDto(memo), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delteMemoById(@PathVariable Long id){
        // memoList의 Key값에 id를 포함하고 있다면
        if(memoList.containsKey(id)){
            memoList.remove(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
