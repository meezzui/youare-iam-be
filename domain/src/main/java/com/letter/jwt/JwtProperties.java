package com.letter.jwt;


public interface JwtProperties{
    String SECRET = ""; // 해싱 메시지 암호화 한 것 넣기
    long EXPIRATION_TIME =  30 * 60 * 1000L; // 유효시간은 30분
    String TOKEN_PREFIX = "Bearer "; // Bearer 뒤에 무조건 띄어쓰기 한번 해주기
    String HEADER_STRING = "Authorization"; // 해더에 Authorization 이라는 토큰을 넣어 줌
}
