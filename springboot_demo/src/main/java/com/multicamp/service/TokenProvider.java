package com.multicamp.service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.multicamp.domain.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//여기서 발급된 token으로 매 api마다 인증해야 함
@Service
public class TokenProvider {
	private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	//public final static Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; // 30 minutes
	//public final static Long REFRESH_TOKEN_EXPIRE_COUNT = 7 * 24 * 60 * 60 * 1000L; // 7 days

	/*
	 * { // header "alg":"HS512" }. { // payload
	 * "sub":"40288093784915d201784916a40c0001", "iss": "demo app",
	 * "iat":1595733657, "exp":1596597657 }. // SECRET_KEY를 이용해 서명한 부분
	 * Nn4d1MOVLZg79sfFACTIpCPKqWmpZMZQsbNrXdJJNWkRv50_l7bPLQPwhMobT4vBOG6Q3JYjhDrKFlBSaUxZOg
	 */
	// JWT Token 생성-만료기한 1시간 설정
	public String create(UserEntity userEntity) {
		// 기한 지금으로부터 1시간로 설정
		Date expiry = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
		return Jwts.builder()
				// 헤더에 들어갈 내용
				.signWith(SECRET_KEY)// signWith 메서드는 지정된 키와 지정된 알고리즘을 사용해 토큰에 서명을합니다.
				// payload에 들어갈 내용
				.setSubject(userEntity.getNickname())// sub 이부분에 닉네임을 저장하는 것에 유의하자
				.setIssuer("demo app")// iss
				.setIssuedAt(new Date())// iat
				.setExpiration(expiry)// exp
				.claim("id", userEntity.getIdx())
				.compact();
	}// ---------------------
	
	//만료기일을 Duration으로 지정하여 JWT Token을 생성하는 메서드
	public String createToken(UserEntity user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }
	
	public String createRefreshToken(UserEntity user, Duration expiredAt) {
		Date now = new Date();
		return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }
	
	private String makeToken(Date expiry, UserEntity userEntity) {
        Date now = new Date();

        return Jwts.builder()
        		.signWith(SECRET_KEY)
                .setIssuer("demo app")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(userEntity.getNickname())
                .claim("id", userEntity.getIdx())
                .compact();
    }
	
	
	/*
	 * parseClaimsJws()메서드가 Base64로 디코딩 및 파싱한다 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해
	 * 서명한 후, 토큰의 서명과 비교 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 발생시킨다. 그 중 우리는
	 * nickname이 필요하므로 getBody()를 부른다.
	 */

	public String validateAndGetUserId(String token) {
		
		Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token)// 토큰을 디코딩 및 파싱함
				.getBody();
		return claims.getSubject();//닉네임 반환
	}// ---------------------
	
	public Claims parseToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
		// 토큰을 디코딩 및 파싱함
		return claims;
	}
	// 토큰의 유효성만 체크하는 메서드
	public boolean validToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);

			return true;
		} catch (Exception e) {
			return false;
		}
	}// ---------------------
	//토큰으로 인증객체 가져오기
	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);
		Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

		return new UsernamePasswordAuthenticationToken(
				new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token,
				authorities);
	}

	private Claims getClaims(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token)// 토큰을 디코딩 및 파싱함
				.getBody();
		return claims;
	}
	//토큰으로 회원번호(idx) 가져오기
	public Long getUserIdx(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }


}
