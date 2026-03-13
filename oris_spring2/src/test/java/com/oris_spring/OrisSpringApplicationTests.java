package com.oris_spring;

import com.oris_spring.persistence.entity.UserEntity;
import com.oris_spring.persistence.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrisSpringApplicationTests {
	@Autowired
	private InMemoryRepository repository;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void saveAndGetUser() {
		UserEntity user = UserEntity.builder()
				.name("Timur")
				.birthDate(LocalDate.of(2000, 1, 10))
				.build();

		repository.save(user);

		assertNotNull(user.getId());
		UserEntity loaded = repository.get(user.getId());
		assertNotNull(loaded);
		assertEquals("Timur", loaded.getName());
		assertEquals(LocalDate.of(2000, 1, 10), loaded.getBirthDate());
	}

	@Test
	void updateExistingUser() {
		UUID id = UUID.randomUUID();
		repository.save(new UserEntity(id, "OldName", LocalDate.of(1999, 2, 2)));

		repository.save(new UserEntity(id, "NewName", LocalDate.of(2001, 3, 3)));

		UserEntity loaded = repository.get(id);
		assertNotNull(loaded);
		assertEquals("NewName", loaded.getName());
		assertEquals(LocalDate.of(2001, 3, 3), loaded.getBirthDate());
	}

	@Test
	void deleteUser() {
		UUID id = UUID.randomUUID();
		repository.save(new UserEntity(id, "DeleteMe", LocalDate.of(1990, 4, 4)));

		assertTrue(repository.delete(id));
		assertNull(repository.get(id));
		assertFalse(repository.delete(id));
	}

	@Test
	void jdbcTemplateThrowsDataIntegrityViolation() {
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("id", UUID.randomUUID().toString())
				.addValue("name", null)
				.addValue("birthDate", LocalDate.now());

		assertThrows(DataIntegrityViolationException.class, () -> jdbcTemplate.update(
				"""
						INSERT INTO users(id, name, birth_date)
						VALUES (:id, :name, :birthDate)
						""",
				params
		));
	}

}
