package com.oris_spring.persistence.repository;

import com.oris_spring.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class InMemoryRepository {
    private static final RowMapper<UserEntity> USER_ROW_MAPPER = (rs, rowNum) -> new UserEntity(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getObject("birth_date", LocalDate.class)
    );
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void save(UserEntity user) {
        if (Objects.isNull(user.getId())) {
            user.setId(UUID.randomUUID());
            jdbcTemplate.update(
                    """
                            INSERT INTO users(id, name, birth_date)
                            VALUES (:id, :name, :birthDate)
                            """,
                    toParams(user)
            );
        } else {
            int updatedRows = jdbcTemplate.update(
                    """
                            UPDATE users
                            SET name = :name,
                                birth_date = :birthDate
                            WHERE id = :id
                            """,
                    toParams(user)
            );

            if (updatedRows == 0) {
                jdbcTemplate.update(
                        """
                                INSERT INTO users(id, name, birth_date)
                                VALUES (:id, :name, :birthDate)
                                """,
                        toParams(user)
                );
            }
        }
    }

    public UserEntity get(UUID id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT id, name, birth_date FROM users WHERE id = :id",
                    new MapSqlParameterSource("id", id.toString()),
                    USER_ROW_MAPPER
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public boolean delete(UUID id) {
        int deletedRows = jdbcTemplate.update(
                "DELETE FROM users WHERE id = :id",
                new MapSqlParameterSource("id", id.toString())
        );
        return deletedRows > 0;
    }

    private MapSqlParameterSource toParams(UserEntity user) {
        return new MapSqlParameterSource()
                .addValue("id", user.getId().toString())
                .addValue("name", user.getName())
                .addValue("birthDate", user.getBirthDate());
    }
}