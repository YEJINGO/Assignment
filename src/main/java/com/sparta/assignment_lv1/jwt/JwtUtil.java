package com.sparta.assignment_lv1.jwt;

import com.sparta.assignment_lv1.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    //Authorization: Bearer <JWT> 에서 Header 에 들어가는 KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // 사용자 권한 값의 KEY: 사용자 권한도 Token 안에 넣는데, 이를 가져올 때 사용하는 KEY 값
    public static final String AUTHORIZATION_KEY = "auth";

    // Token 식별자: Token 을 만들 때, 앞에 들어가는 부분
    private static final String BEARER_PREFIX = "Bearer ";

    // 토큰 만료시간: 밀리세컨드 기준. 60 * 1000L는 1분. 60 * 60 * 1000L는 1시간
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    //@Value("${프로퍼티 키값}") : application.properties 에 정의한 내용을 가져와서 사용 가능 (수정과 관리가 용이하기 때문에 이렇게 사용)
    @Value("${jwt.secret.key}")
    //@Value() 안에 application.properties 에 넣어둔 KEY 값(jwt.secret.key=7ZWt7ZW0O...pA=)을 넣으면, 가져올 수 있음
    private String secretKey;
    //Key 객체: Token 을 만들 때 넣어줄 KEY 값
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    //메소드
    //종속성 주입이 완료된 후 실행되어야 하는 메서드에 사용
    //@PostConstruct 는 의존성 주입이 이루어진 후 초기화를 수행하는 메서드
    //사용 이유?
    //1. 생성자가 호출되었을 때, 빈은 초기화되지 않았음(의존성 주입이 이루어지지 않았음)
    //2. 어플리케이션이 실행될 때 bean 이 오직 한 번만 수행되게 해서  bean 이 여러 번 초기화되는 걸 방지
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey); //Base64로 인코딩되어 있는 것을, 값을 가져와서(getDecoder()) 디코드하고(decode(secretKey)), byte 배열로 반환
        key = Keys.hmacShaKeyFor(bytes); //반환된 bytes 를 hmacShaKeyFor() 메서드를 사용해서 Key 객체에 넣기
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request) {
        //HttpServletRequest request 객체 안에 들어간 Token 값을 가져옴
        //() 안에는 어떤 KEY 를 가져올지 정할 수 있음(여기선 AUTHORIZATION_HEADER 안에 있는 KEY 의 값을 가져옴)
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            //substring() 메소드를 사용해서, 앞의 일곱 글자를 지워줌 --> 앞의 일곱 글자는 Token 과 관련 없는 String 값이므로
            return bearerToken.substring(7);
        }
        return null;
    }

    // JWT 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +  //Token 앞에 들어가는 부분임
                Jwts.builder()
                        //setSubject 라는 공간 안에 username 넣는다.
                        .setSubject(username)
                        //claim 이라는 공간 안에 AUTHORIZATION_KEY 사용자의 권한을 넣고(이 권한을 가져올 때는 지정해둔 auth KEY 값을 사용해서 넣음)
                        .claim(AUTHORIZATION_KEY, role)
                        //이 Token 을 언제까지 유효하게 가져갈건지. date: 위의 Date date = new Date() 에서 가져온 부분
                        //+ TOKEN_TIME: 우리가 지정해 둔 시간(TOKEN_TIME = 60 * 60 * 1000L)을 더한다 = 즉, 지금 기준으로 언제까지 가능한지 결정해줌
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        //Token 이 언제 만들어졌는지 넣는 부분
                        .setIssuedAt(date)
                        //secret key 를 사용해서 만든 key 객체와
                        //어떤 암호화 알고리즘을 사용해서 암호화할것인지 지정(SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256)
                        .signWith(key, signatureAlgorithm)
                        //반환
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            //Jwts: 위에서 JWT 생성 시 사용했던 것
            //parserBuilder(): 검증 방법
            //setSigningKey(key): Token 을 만들 때 사용한 KEY
            //parseClaimsJws(token): 어떤 Token 을 검증할 것인지
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    // 토큰에서 사용자 정보 가져오기 --> 위에서 validateToken() 으로 토큰을 검증했기에 이 코드를 사용할 수 있는 것
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }



}
