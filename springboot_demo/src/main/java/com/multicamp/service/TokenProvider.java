package com.multicamp.service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.multicamp.domain.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
//여기서 발급된 token으로 매 api마다 인증해야 함
@Service
public class TokenProvider {
	private static final Key SECRET_KEY=Keys.secretKeyFor(SignatureAlgorithm.HS512);
	/*
	  { // header
	    "alg":"HS512"
	  }.
	  { // payload
	    "sub":"40288093784915d201784916a40c0001",
	    "iss": "demo app",
	    "iat":1595733657,
	    "exp":1596597657
	  }.
	  // SECRET_KEY를 이용해 서명한 부분
	  Nn4d1MOVLZg79sfFACTIpCPKqWmpZMZQsbNrXdJJNWkRv50_l7bPLQPwhMobT4vBOG6Q3JYjhDrKFlBSaUxZOg
	   */
	    // JWT Token 생성
	public String create(UserEntity userEntity) {
		//기한 지금으로부터 1일로 설정
		Date expiry=Date.from(Instant.now().plus(1,ChronoUnit.DAYS));
		return Jwts.builder()
		//헤더에 들어갈 내용
		.signWith(SECRET_KEY)//signWith 메서드는 지정된 키와 지정된 알고리즘을 사용해	토큰에 서명을합니다.
		//payload에 들어갈 내용
		.setSubject(userEntity.getNickname()+"")//sub 이부분에 닉네임을 저장하는 것에 유의하자
		.setIssuer("demo app")//iss
		.setIssuedAt(new Date())//iat
		.setExpiration(expiry)//exp
		.compact();
	}//---------------------
	/*parseClaimsJws()메서드가 Base64로 디코딩 및 파싱한다
	 * 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후, 토큰의 서명과 비교
	 * 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 발생시킨다.
	 * 그 중 우리는 nickname이 필요하므로 getBody()를 부른다.
	 * */
	public String validateAndGetUserId(String token) {
		
		Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)//토큰을 디코딩 및 파싱함
                .getBody();  
		return claims.getSubject();
	}//---------------------

}
