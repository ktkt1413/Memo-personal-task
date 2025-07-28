package com.sparta.memo_personal_task.repository;

import com.sparta.memo_personal_task.dto.MemoResponseDto;
import com.sparta.memo_personal_task.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcTemplateMemoRepository implements MemoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MemoResponseDto saveMemo(Memo memo) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("memo").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", memo.getTitle());
        parameters.put("contents", memo.getContents());
        parameters.put("username", memo.getUsername());

        //저장 후 생성된 key값 Number 타입을 반환하는 메서드: executeAndReturnKey()-> 내부적으로 insert into memo() values()를 작동 및 실행
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new MemoResponseDto(key.longValue(), memo.getTitle(), memo.getContents(), memo.getUsername());
    }

    // 단건 조회
//    @Override
//    public MemoResponseDto findMemoById(Long id) {
//        List<MemoResponseDto> result = jdbcTemplate.query("select * from memo where id =?", memoRowMapper(), id);
//        if(result.isEmpty()){
//            throw new NoSuchElementException("해당 메모가 존재하지 않습니다.");
//        }
//        return result.get(0);
//    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        return jdbcTemplate.query("select * from memo", memoRowMapper());
    }

    // 외부 API를 받아올 때(keyword)는 예상하지 못한 null대비로 optional을 사용해야함
    @Override
    public List<MemoResponseDto> getMemosByKeyword(String keyword) {
        if(keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String sql = "select * from memo where contents like ?";
        String likeKeyword = "%" + keyword + "%";
        List<MemoResponseDto> result = jdbcTemplate.query(sql, memoRowMapper(), likeKeyword);

        return result;
    }

    @Override
    public List<MemoResponseDto> findMemosByUsername(String username) {
        if(username == null || username.trim().isEmpty()){
            return Collections.emptyList();
        }
        String sql = "select * from memo where username like ?";
        String likeUsername = "%" + username + "%";
        List<MemoResponseDto> result = jdbcTemplate.query(sql, memoRowMapper(), likeUsername);

        return result;
    }

    @Override
    public Memo findMemoByIdOrElseThrow(Long id) {
        List<Memo> result = jdbcTemplate.query("select * from memo where id =?", memoRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "해당메모가 존재하지 않습니다. id: " + id));
    }

    //jdbcTemplate.update() -> 몇 개의 행이 업데이트되었는지 숫자를 반환하므로 int로 받는다.
    @Override
    public int updateMemo(Long id, String title, String contents) {
        return jdbcTemplate.update("update memo set title = ?, contents =? where id =?", title, contents, id);
    }

    @Override
    public int deleteMemoById(Long id) {
        return jdbcTemplate.update("delete from memo where id = ?", id);
    }

    private RowMapper<MemoResponseDto> memoRowMapper(){
        return new RowMapper<MemoResponseDto>(){
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MemoResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("username")
                );
            };

        };
    }

    private RowMapper<Memo> memoRowMapperV2(){
        return new RowMapper<Memo>(){
            @Override
            public Memo mapRow(ResultSet rs, int rowNum) throws SQLException{
                return new Memo(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("username")
                );
            };
        };
    }

}
