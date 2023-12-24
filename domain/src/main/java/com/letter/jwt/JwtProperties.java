package com.letter.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties{

    @Value("${jwt.secret}")
    public static String SECRET; // 해싱 메시지 암호화 한 것 넣기
    public static final long EXPIRATION_TIME =  30 * 60 * 1000L; // 유효시간은 30분
    public static final String TOKEN_PREFIX = "Bearer "; // Bearer 뒤에 무조건 띄어쓰기 한번 해주기
    public static final String HEADER_STRING = "Authorization"; // 해더에 Authorization 이라는 토큰을 넣어 줌
}
