//package com.lakefarm.utils;
///**
// * Created by rxl on 17-2-28.
// */
//
//import com.lakefarm.contants.Const;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//
//public class JedisUtil {
//
//
////    public Jedis jedisClient;
//    private JedisPool pool = null;
////    maxActive
////    控制池中对象的最大数量。默认值是8，如果是负值表示没限制。
////    maxIdle
////    控制池中空闲的对象的最大数量。默认值是8，如果是负值表示没限制。
////    minIdle
////    控制池中空闲的对象的最小数量。默认值是0。
////    whenExhaustedAction
////    指定池中对象被消耗完以后的行为，有下面这些选择：
////            >> WHEN_EXHAUSTED_FAIL		0
////            >> WHEN_EXHAUSTED_GROW		2
////            >> WHEN_EXHAUSTEdD_BLOCK		1
//
//    public JedisUtil(){
////        初始化连接池
//        JedisPoolConfig config = new JedisPoolConfig();
//        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
//        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
//        config.setMaxIdle(100);
//        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
//        config.setMaxWaitMillis(1000 * 30);
//        config.setMaxTotal(1000);
//        pool = new JedisPool(config, Params.SEVER_ADDRESS,Params.SEVER_PORT);
//
//
//    }
//
//    public void recoverJedis(Jedis jedis){
//        System.out.println("jedis 回收");
//        pool.returnResourceObject(jedis);
//    }
//
//    public boolean exists(String key){
//        Jedis jedis = pool.getResource();
//        boolean exists = jedis.exists(key);
//        recoverJedis(jedis);
//        return exists;
//    }
//   public String hmset(String key ,Map <String,String> hash){
//       System.out.println("jedis get("+key+") ");
//       Jedis jedis = pool.getResource();
//       String value = jedis.hmset(key ,hash);
//       recoverJedis(jedis);
//       return value;
//   }
//
//    public String get(String key) {
//        System.out.println("jedis get("+key+") ");
//        Jedis jedis = pool.getResource();
//        String value = jedis.get(key);
//        recoverJedis(jedis);
//        return value;
//    }
//    public List<String> lrange(String key) {
//
//        System.out.println("jedis get("+key+") ");
//        Jedis jedis = pool.getResource();
//        Long length=jedis.llen(key);
//        List<String> value = jedis.lrange(key,0,-1);
//        recoverJedis(jedis);
//        return value;
//    }
//    public Long lpush(String key,String data) {
//        System.out.println("jedis get("+key+") ");
//        Jedis jedis = pool.getResource();
//        Long value = jedis.lpush(key,data);
//        recoverJedis(jedis);
//        return value;
//    }
//
//    public String hget(String key,String field){
//        Jedis jedis = pool.getResource();
//        String value = jedis.hget(key, field);
//        recoverJedis(jedis);
//        return value;
//    }
//    public Boolean hexists(String key, String field){
//        Jedis jedis = pool.getResource();
//        Boolean value = jedis.hexists(key ,field);
//        recoverJedis(jedis);
//        return value;
//    }
//    public Long hdel(String key, String field){
//        Jedis jedis = pool.getResource();
//        Long value = jedis.hdel(key,field);
//        recoverJedis(jedis);
//        return value;
//    }
//    public Long hset(String key, String field,String value){
//        Jedis jedis = pool.getResource();
//        Long result= jedis.hset(key,field,value);
//        recoverJedis(jedis);
//        return result;
//    }
//    public Set<String> hkeys(String key){
//        Jedis jedis = pool.getResource();
//        Set<String> value = jedis.hkeys(key);
//        recoverJedis(jedis);
//        return value;
//    }
//    public Set<String> hvals(String key){
//        Jedis jedis = pool.getResource();
//        Set<String> value = (Set<String>) jedis.hvals(key);
//        recoverJedis(jedis);
//        return value;
//    }
//
//    public Map<String,String> hgetAll(String key){
//        Jedis jedis = pool.getResource();
//        Map<String,String> value = jedis.hgetAll(key);
//        recoverJedis(jedis);
//        return value;
//    }
//
//    public Set<String> keys(String pattern){
//        Jedis jedis = pool.getResource();
//        Set<String> value = jedis.keys(pattern);
//        recoverJedis(jedis);
//        return value;
//    }
//
//
//    public void set(String key, String value) {
//        Jedis jedis = pool.getResource();
//        jedis.set(key, value);
//        recoverJedis(jedis);
//    }
//
//    public void setList(String listname, List<String> list) {
//        Jedis jedis = pool.getResource();
//        for (String item : list) {
//            jedis.lpush(listname, item);
//        }
//        recoverJedis(jedis);
//    }
//
//    public List<String> getList(String listname) {
//        Jedis jedis = pool.getResource();
//        List<String> value = jedis.lrange(listname, 0, -1);
//        recoverJedis(jedis);
//        return value;
//    }
//
//    public float getMemoryUsed(){
//        Jedis jedis = pool.getResource();
//        System.out.println(jedis.info(Params.MEMORY));
//        String [] line = jedis.info(Params.MEMORY).split("\n");
//        String used_memory = line[1].split(":")[1];
//        System.out.println("used_memory : "+used_memory);
//        recoverJedis(jedis);
//        return Float.parseFloat(used_memory)/1024;
//    }
//
//    public void incr(String key){
//        Jedis jedis = pool.getResource();
//        jedis.incr(key);
//        recoverJedis(jedis);
//    }
//
//    public void decr(String key){
//        Jedis jedis = pool.getResource();
//        jedis.decr(key);
//        recoverJedis(jedis);
//    }
//
//    public void clear(String key){
//        Jedis jedis = pool.getResource();
//        jedis.del(key);
//        recoverJedis(jedis);
//    }
//
//    public void clear(){
//        Jedis jedis = pool.getResource();
//        jedis.flushAll();
//        recoverJedis(jedis);
//    }
//
//    public void zincr(String key,int score,String member){
//        Jedis jedis = pool.getResource();
//        jedis.zincrby(key,score,member);
//        recoverJedis(jedis);
//    }
//}
