package com.sparta.memo_personal_task.repository;

import org.springframework.jdbc.core.JdbcTemplate;

public class MemoRepository {

    private final JdbcTemplate jdbctemplate;

    public MemoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
