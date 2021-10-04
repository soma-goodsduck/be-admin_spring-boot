package com.ducks.goodsduck.admin.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class EmailAuthenticationRepository {

    private final String PREFIX = "email:";
    private final int LIMIT_TIME = 60 * 30;

    private final StringRedisTemplate stringRedisTemplate;

    public void setCode(String email, String code) {
        stringRedisTemplate.opsForValue()
                .set(PREFIX + email, code, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getCode(String email) {
        return stringRedisTemplate.opsForValue().get(PREFIX + email);
    }

    public void deleteCode(String email) {
        stringRedisTemplate.delete(PREFIX + email);
    }

    public boolean hasKey(String email) {
        return stringRedisTemplate.hasKey(PREFIX + email);
    }
}
