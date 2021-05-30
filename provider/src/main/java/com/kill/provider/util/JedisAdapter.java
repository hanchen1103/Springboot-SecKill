package com.kill.provider.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * redis配置及关于jedis的函数实现
 */
@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("reids://localhost:6379/10");
    }

    public long sdel(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.del(key);
        } catch (Exception  e) {
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }


    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception  e) {
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long expire(String key, int sec) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.expire(key, sec);
        } catch (Exception  e) {
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch ( Exception e) {
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public List<String> brpop(int timeout, String key) { //移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("error" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("error" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);
        } catch(Exception e) {
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            logger.error("error" + e.getMessage());
        }
        finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long zadd(String key, double score, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.zadd(key, score, value);
        } catch (Exception e) {
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis == null) {
                jedis.close();
            }
        }
        return 0L;
    }

    public long zrem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrem(key, value);
        } catch (Exception e){
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return 0L;
    }

    public long hset(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            hsetNum(key, value, "0");
            return jedis.hset("REDIS_NUM", key, value);
        } catch (Exception e){
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return 0L;
    }

    public String setex(String key, String value, int sec) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.setex(key, sec, value);
        } catch (Exception e){
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e){
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long hsetNum(String key, String value, String count) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hset("REDIS_NUM", key + value, count);
        } catch (Exception e){
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return 0L;
    }

    public boolean hexists(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hexists("REDIS_NUM", key);
        } catch (Exception e){
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public long hdel(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hdel("REDIS_NUM", key);
        } catch (Exception e) {
            logger.error("error" + e.getMessage());
        }
        return 0l;
    }

    public String hget(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hget("REDIS_NUM", key);
        } catch (Exception e){
            logger.error("error" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Transaction multi(Jedis jedis) { //multi命令用于开启一个事务，它总是返回 OK 。
        try {
            return jedis.multi();
        } catch (Exception e) {
            logger.error("error" + e.getMessage());
        } finally {

        }
        return null;
    }

    public List<Object> exec(Transaction ts, Jedis jedis) { //MULTI 执行之后， 客户端可以继续向服务器发送任意多条命令，
        // 这些命令不会立即被执行，而是被放到一个队列中，当 EXEC 命令被调用时，所有队列中的命令才会被执行。
        try {
            return ts.exec();
        } catch(Exception e) {
            logger.error("error" + e.getMessage());
            ts.discard(); //通过调用 DISCARD 客户端可以清空事务队列，并放弃执行事务。
        } finally{
            if(ts != null){
                try {
                    ts.close();
                } catch(Exception e) {
                    return null;
                }
            }
            if(jedis != null) {
                jedis.close();
            }

        }
        return null;
    }

    public Set<String> zrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrange(key, start, end);

        } catch (Exception e) {
            logger.error("error" + e.getMessage());

        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrevrange(key, start, end);

        } catch (Exception e) {
            logger.error("error" + e.getMessage());

        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);

        } catch (Exception e) {
            logger.error("error" + e.getMessage());

        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return 0L;
    }

    public Double zscore(String key, String memeber) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zscore(key, memeber);
        } catch (Exception e) {
            logger.error("errro" + e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Jedis getJedis() {
        return pool.getResource();
    }
    /*
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();
        jedis.set("hello", "world");
        jedis.rename("hello", "newworld");
        jedis.setex("diaozi", 10, "short");
        String listname = "list";
        for(int i = 1; i <= 10; i ++) {
            jedis.lpush(listname, "zhc" + String.valueOf(i));
        }
        System.out.println(jedis.lrange(listname, 0, 9));

        User user = new User();
        user.setHead_url("asda.png");
        user.setSalt("aaaabbb");
        user.setName("xbu");
        user.setPassword("xburenr");
        user.setId(346);
        jedis.set("user1", JSONObject.toJSONString(user));
        String value = jedis.get("user1");
        User user2 = JSON.parseObject(value, user.getClass());
        System.out.println(user2);
    }

     */
}
