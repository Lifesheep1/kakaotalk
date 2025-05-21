package com.example.kakaotalk;

import com.example.kakaotalk.entity.User;
import com.example.kakaotalk.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KakaotalkApplicationTests {

	@Autowired
	private UserRepository userRepository;

	private final String TEST_EMAIL = "test@example.com";

	@BeforeEach
	void setUp() {
		// 테스트 실행 전 기존 데이터 삭제
		userRepository.deleteAll();
		// 테스트용 User 준비
		User user = new User();
		user.setUsername("testuser");
		user.setEmail(TEST_EMAIL);
		user.setPassword("hashedpwd");
		userRepository.save(user);
	}

	@Test
	void insertAndFindUser() {
		// 이메일로 조회
		Optional<User> found = userRepository.findByEmail(TEST_EMAIL);
		assertThat(found).isPresent();
		assertThat(found.get().getUsername()).isEqualTo("testuser");
	}

	@Test
	void uniqueEmailConstraint() {
		// 동일 이메일로 삽입 시 예외 발생 확인
		User dup = new User();
		dup.setUsername("another");
		dup.setEmail(TEST_EMAIL);
		dup.setPassword("pwd");

		org.junit.jupiter.api.Assertions.assertThrows(
				org.springframework.dao.DataIntegrityViolationException.class,
				() -> userRepository.saveAndFlush(dup)
		);
	}
}
