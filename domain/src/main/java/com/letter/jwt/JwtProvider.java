package com.letter.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letter.exception.CustomException;
import com.letter.exception.ErrorCode;
import com.letter.member.dto.OAuthResponse;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtProvider {
    private static final Long REFRESH_TOKEN_VALID_TIME = Duration.ofDays(14).toMillis(); // 만료시간 2주

    @Value("${jwt.secret}")
    private String secretKey;

    public static final long EXPIRATION_TIME =  30 * 60 * 1000L; // 유효시간은 30분
    public static final String TOKEN_PREFIX = "Bearer "; // Bearer 뒤에 무조건 띄어쓰기 한번 해주기
    public static final String HEADER_STRING = "Authorization"; // 해더에 Authorization 이라는 토큰을 넣어 줌
    /**
     * jwt 토큰 생성하기
     * @param userInfo
     * @return
     */
    public String createAccessToken(String memberId, OAuthResponse userInfo) {

        return JWT.create()
                .withSubject(memberId) // Payload 에 들어갈 등록된 클레임 을 설정한다.
                .withExpiresAt(new Date(System.currentTimeMillis()+ EXPIRATION_TIME)) //JwtProperties 의 만료 시간 필드를 불러와 넣어준다.
                .withClaim("nickname", userInfo.getNickname()) // Payload 에 들어갈 개인 클레임 을 설정한다.
                // .withClaim(이름, 내용) 형태로 작성한다. 사용자를 식별할 수 있는 값과, 따로 추가하고 싶은 값을 자유롭게 넣는다.
                .sign(Algorithm.HMAC512(secretKey));
    }


    public String createAccessToken(String memberId, String nickname) {

        return JWT.create()
                .withSubject(memberId) // Payload 에 들어갈 등록된 클레임 을 설정한다.
                .withExpiresAt(new Date(System.currentTimeMillis()+ EXPIRATION_TIME)) //JwtProperties 의 만료 시간 필드를 불러와 넣어준다.
                .withClaim("nickname", nickname) // Payload 에 들어갈 개인 클레임 을 설정한다.
                // .withClaim(이름, 내용) 형태로 작성한다. 사용자를 식별할 수 있는 값과, 따로 추가하고 싶은 값을 자유롭게 넣는다.
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * 헤더에서 토큰 가져오기
     * @param request
     * @return
     */
    public String bringToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7); // "Bearer " 을 제외한 토큰만 가져오기
        }
        return null;
    }

    /**
     * 토큰 검증
     * @param token
     * @return
     */
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다. 토큰이 비었을 수 있으니 다시 확인해주세요.");
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 토큰에서 사용자 정보 가져오기
     * @param token
     * @return
     */
    public String getUserInfoFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("sub", String.class);
    }



    /**
     * Refresh token 생성
     * @return Jwt Refresh token
     */
    public String createRefreshToken() {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME))
                .sign(Algorithm.HMAC512(secretKey));
    }


    /**
     * 만료된 Access token 으로 claims 꺼내기
     * @param accessToken
     * @return
     */
    public String getMemberIdByExpiredJwt(String accessToken) {
        final String[] tokenParts = accessToken.split("\\.");
        final String jsonData = new String(Base64.getDecoder().decode(tokenParts[1]));

        // TODO JWT 시크릿 키 검증

        Map claims = null;
        try {
            claims = new ObjectMapper().readValue(jsonData, Map.class);
        } catch (JsonProcessingException exception) {
            log.error("ObjectMapper로 Json 파싱 실패");
            log.error(exception.getMessage());
        }

        return (String) claims.get("sub");
    }

}
