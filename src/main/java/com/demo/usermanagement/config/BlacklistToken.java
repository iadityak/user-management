package com.demo.usermanagement.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BlacklistToken {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public void setToken(String token, Long EXPIRE_TIME) {
		redisTemplate.opsForValue().set(token, "1");
		redisTemplate.expire(token, EXPIRE_TIME, TimeUnit.SECONDS);
	}
	
	public String getToken(String token) {
		return (String) redisTemplate.opsForValue().get(token);
	}
}
