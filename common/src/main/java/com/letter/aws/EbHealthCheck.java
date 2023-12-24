package com.letter.aws;

import com.letter.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EbHealthCheck {

    private final JwtProvider jwtProvider;

    @GetMapping("/")
    public ResponseEntity<String> doHealthCheck() {
        return ResponseEntity.ok("Health Check OK");
    }

    @GetMapping("/test")
    public String test() {
        return jwtProvider.getSECRET();
    }
}
