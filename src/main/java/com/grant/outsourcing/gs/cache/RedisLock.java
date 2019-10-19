package com.grant.outsourcing.gs.cache;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLock
{
	private static final String CACHE_SET_KEY_PRE = RedisLock.class.getName();

	private static final int DEFAULT_MAX_BLOCKING_TIME = 10000;

	@Autowired RedissonClient redissonClient;

	/**
	 * 阻塞锁
	 *
	 * @param lockName
	 * @param key
	 * @return
	 */
	public boolean lock(String lockName, String key) {
		return lock(lockName, key, DEFAULT_MAX_BLOCKING_TIME);
	}

	/**
	 * 阻塞锁，最大阻塞时间为waitingTime
	 *
	 * @param lockName
	 * @param key
	 * @param waitingTime
	 */
	public boolean lock(String lockName, String key, int waitingTime) {
		String lockKey = getLockKey(lockName, key);
		RLock lock = redissonClient.getLock(lockKey);
		try {
			return lock.tryLock(waitingTime, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			return false;
		}
	}

	/**
	 * 非阻塞锁
	 * 抢锁失败直接返回false
	 *
	 * @param lockName
	 * @param key
	 * @return
	 */
	public boolean lockWithoutBlocking(String lockName, String key) {
		return lock(lockName, key);
	}

	/**
	 * 解锁
	 *
	 * @param lockName
	 * @param key
	 */
	public void unlock(String lockName, String key) {
		String lockKey = getLockKey(lockName, key);
		RLock lock = redissonClient.getLock(lockKey);
		lock.unlock();
	}

	private String getLockKey(String lockName, String key) {
		return String.format("%s::%s::%s", CACHE_SET_KEY_PRE, lockName, key);
	}
}
