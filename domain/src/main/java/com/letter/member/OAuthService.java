package com.letter.member;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.JsonElement;
import com.letter.jwt.JwtProperties;
import com.letter.member.dto.OAuthResponse;
import com.letter.member.entity.Member;
import com.letter.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.google.gson.JsonParser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final MemberRepository memberRepository;

    /**
     * 카카오 사용자 회원정보 담긴 것 가져오기
     * 회원 정보가 디비에 없으면 저장하고 있으면 바로 access token 발급
     * @param code
     * @return
     */
    @Transactional
    public ResponseEntity getOAuthInfo(String code){
        // dto에 담은 사용자 정보 가져오기
        OAuthResponse userInfo = getKakaoUserInfo(code);
        // 이메일과 미디어 구분으로 디비에 해당 회원 정보가 있는지 조회
        // TODO: findByAndEmailAndMediaSeparator -> findByEmailAndMediaSeparator 로 이름 수정하기
        Optional<Member> member = memberRepository.findByAndEmailAndMediaSeparator(userInfo.getEmail(), "kakao");

        // 회원 정보가 있으면 디비에 저장없이 토큰 발급
        // 회원 정보가 없으면 정보를 디비에 저장하고 토큰 발급
        if(member.isEmpty()){
            // 총 회원 수 + 1
            Long memberCount = memberRepository.countAllBy() + 1;
            //디비에 회원 정보 저장(회원 아이디,이메일,이름,미디어 구분 값,리프레쉬 토큰)
            // Member 엔티티에 값 셋팅
            Member member1 = new Member();
            member1.saveUserInfo(userInfo, memberCount);
            // 디비에 저장
            memberRepository.save(member1);
        }

        // jwt 토큰 생성
        String jwtToken = createJwtToken(userInfo);
        //헤더에 토큰 담아주기
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX + jwtToken);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(null);
    }

    /**
     * jwt 토큰 생성하기
     * @param userInfo
     * @return
     */
    private String createJwtToken(OAuthResponse userInfo) {

        String jwtToken = JWT.create()
                .withSubject(userInfo.getEmail()) // Payload 에 들어갈 등록된 클레임 을 설정한다.
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME)) //JwtProperties 의 만료 시간 필드를 불러와 넣어준다.
                .withClaim("nickname", userInfo.getNickname()) // Payload 에 들어갈 개인 클레임 을 설정한다.
                                                                     // .withClaim(이름, 내용) 형태로 작성한다. 사용자를 식별할 수 있는 값과, 따로 추가하고 싶은 값을 자유롭게 넣는다.
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 사용할 암호화 알고리즘과 secret 값 셋팅
        return jwtToken;
    }


    /**
     * 카카오에서 access token 가져오기
     * @param code
     * @return
     */
    public String getKakaoAccessToken (String code) {
        String access_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=8a4726cfed8673405f24582af1d2ff15"); // REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:8080/api/v1/members/kakao/callback"); // 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();

            System.out.println("access_token : " + access_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;

    }

    /**
     * 카카오에서 가져온 access token으로 사용자 정보 조회 후 dto에 담기
     * @param code
     * @return
     */
    public OAuthResponse getKakaoUserInfo(String code){

        // 카카오에서 access token 가져오기
        String accessToken = getKakaoAccessToken(code);


        String reqURL = "https://kapi.kakao.com/v2/user/me"; // 카카오에서 정보를 가져오기 위한 url
        String email = "";
        String nickname = "";
        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + accessToken); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            // 이메일이 있는지 여부
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            // "kakao_account" 객체 안에 "profile" 객체 안에 nickname 가져오기
            nickname = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();

            // 이메일이 있을 경우만 가져오기
            if(hasEmail){
                // "kakao_account" 객체 안에 email 가져오기
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            System.out.println("nickname : " + nickname);
            System.out.println("email : " + email);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return OAuthResponse.builder()
                .nickname(nickname)
                .email(email)
                .build();
    }

}





