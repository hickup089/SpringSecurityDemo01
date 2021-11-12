package com.example.springsecuritydemo01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenRepositoryImpl implements PersistentTokenRepository {

    private final static  String SERIES_KEY="SECURITY_SERIES_KEY";
    private final static  String USERNAME_KEY="SECURITY_USERNAME_KEY";

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void createNewToken(PersistentRememberMeToken prt) {

          String token=  prt.getTokenValue();
          String series=prt.getSeries();
          String username=prt.getUsername();

          String series_key_redis=GetKey(series,SERIES_KEY);
          String username_key=GetKey(username, USERNAME_KEY);

          // 重新用密码账号登录后，把之前的删掉
    //     deleteForUserToken(username_key);

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("username",username);
        hashMap.put("token",token);
        hashMap.put("date",String.valueOf(prt.getDate().getTime()));

        HashOperations<String,String,String> hashOperations=redisTemplate.opsForHash();
        hashOperations.putAll(series_key_redis,hashMap);
        // 设置key的过期事件，这里是1天
        redisTemplate.expire(series_key_redis,1, TimeUnit.DAYS);

       redisTemplate.opsForValue().set(username_key,series);
        redisTemplate.expire(username_key,1, TimeUnit.DAYS);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {

        String series_key_redis=GetKey(series,SERIES_KEY);
        if (redisTemplate.hasKey(series_key_redis)){
            redisTemplate.opsForHash().put(series_key_redis,"token",tokenValue);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {

        String series_key_redis=GetKey(series,SERIES_KEY);
        List<String> hashKeys=new ArrayList<>();
        hashKeys.add("username");
        hashKeys.add("token");
        hashKeys.add("date");
         List<String> hasValues= redisTemplate.opsForHash().multiGet(series_key_redis,hashKeys);
         String username=hasValues.get(0);
         String tokenValue=hasValues.get(1);
         String date=hasValues.get(2);
         if(username==null||tokenValue==null||date==null){
             return null;
         }
         Long timestamp=Long.valueOf(date);
         Date time= new Date(timestamp);

        return new PersistentRememberMeToken(username,series,tokenValue,time);
    }

    @Override
    public void removeUserTokens(String username) {
        // 这里传入的是username
        String username_key=GetKey(username, USERNAME_KEY);
        deleteForUserToken(username_key);

    }

    private void deleteForUserToken(String username_key){

        if (redisTemplate.hasKey(username_key)){
            String series=GetKey(redisTemplate.opsForValue().get(username_key).toString(),SERIES_KEY);
            if (series!=null&&redisTemplate.hasKey(series)){
                redisTemplate.delete(username_key);
                redisTemplate.delete(series);
            }
        }
    }

    private String GetKey(String key,String k2){
        return key+k2;
    }
}
