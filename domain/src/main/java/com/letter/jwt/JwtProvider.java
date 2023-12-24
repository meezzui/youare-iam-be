package com.letter.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.letter.exception.CustomException;
import com.letter.exception.ErrorCode;
import com.letter.member.dto.OAuthResponse;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {
    private static final Long refreshTokenValidTime = Duration.ofDays(14).toMillis(); // 만료시간 2주

    /**
     * jwt 토큰 생성하기
     * @param userInfo
     * @return
     */
    public String createJwtToken(String memberId, OAuthResponse userInfo) {

        String jwtToken = JWT.create()
                .withSubject(memberId) // Payload 에 들어갈 등록된 클레임 을 설정한다.
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME)) //JwtProperties 의 만료 시간 필드를 불러와 넣어준다.
                .withClaim("nickname", userInfo.getNickname()) // Payload 에 들어갈 개인 클레임 을 설정한다.
                // .withClaim(이름, 내용) 형태로 작성한다. 사용자를 식별할 수 있는 값과, 따로 추가하고 싶은 값을 자유롭게 넣는다.
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 사용할 암호화 알고리즘과 secret 값 셋팅
        return jwtToken;
    }

    /**
     * 헤더에서 토큰 가져오기
     * @param request
     * @return
     */
    public String bringToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProperties.HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return bearerToken.substring(7); // "Bearer " 을 제외한 토큰만 가져오기
        }
        return null;
    }

    /**
     * 토큰 검증
     * @param token
     * @return
     */
    public boolean validateToken(String token, String secretKey) {

        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
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
    public String getUserInfoFromToken(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("sub", String.class);
    }

//    /**
//     * refresh 토큰 확인
//     * @param token
//     * @param secretKey
//     * @return
//     */
//    public static boolean isRefreshToken(String token, String secretKey) {
//
//        Header header = Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getHeader();
//
//        if (header.get("type").toString().equals("refresh")) {
//            return true;
//        }
//        return false;
//    }

//    /**
//     * refresh 토큰 생성
//     * @param memberId
//     * @param userInfo
//     * @return
//     */
//    public static String createRefreshToken(String memberId, OAuthResponse userInfo) {
//        return createJwtToken(memberId, userInfo);
//    }

}
