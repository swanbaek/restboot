package com.multicamp.service;


import java.util.Optional;

import org.springframework.stereotype.Service;
/*
  Diary/우아한테크코스 2022. 7. 29. 09:59
 https://easthshin.tistory.com/12
우테코 내에서 팀 프로젝트로 속닥속닥 이라는 익명 커뮤니티를 개발하고 있다. 현재 인증/인가를 JWT를 이용해 구현했는데, 
토큰 만료 시간이 지났을 경우 로그인이 풀리는 문제가 있다. 토큰 만료로 갑작스레 로그인이 풀린다면 사용자는 불편을 겪을 것이다. 
예를 들어, 속닥속닥 사용자가 게시판에 글을 열심히 쓰고, 작성 버튼을 눌러 글 작성을 완료하려 한다. 
하지만, 작성하는 도중에 토큰이 만료되었고 글 작성 버튼을 누르는 순간 로그인 화면으로 돌아가 하던 작업을 잃게 된다. 
이런 상황을 방지하기 위해 토큰이 만료된다면 자동으로 다시 발급해주어 로그인이 풀리지 않게 구현하려 한다. 이를 위해, Refresh Token을 이용하면 된다.
------------------------
자동로그인 동작 시나리오
1. 로그인하면 access token과 refresh token을 생성해 둘 다 발급해준다. 이 때, refresh token은 db에 저장한다.
2. 클라이언트에서 요청을 보낼때 마다 access token을 담아 서버에 요청한다.

3.a
  1. 만료된 토큰을 담아 요청하면, 서버에서 401 응답을 보낸다.
  2. 401 응답을 받은 클라이언트는 access token과 refresh token을 같이 보낸다.
3.b 클라이언트에서 access token의 payload를 통해 만료기간이 지났다는 것을 확인해 바로 refresh 요청을 보낸다. (이렇게 하면 네트워크 요청을 줄일 수 있다.)

4. refresh 요청을 받은 서버는 만료된 access token을 통해 회원 정보를 뽑아내고, 그 정보와 매치되는 refresh token을 db에서 가져온다.
5.  가져온 refresh token을 클라이언트로부터 받은 refresh token과 일치하는지 확인한다.
6.a. 일치한다면, 새로운 access token을 발급해준다.
6.b. 일치하지 않는다면, refresh token이 유효하지 않은 것이니 401 응답을 보내고 클라이언트는 재 로그인을 하게된다.

refresh token이 만료되었다면 해당 refresh token을 db에서 제거하고 401 응답을 보내 재 로그인 하도록 한다.
 * */
import org.springframework.transaction.annotation.Transactional;

import com.multicamp.cmm.exception.InvalidRefreshTokenException;
import com.multicamp.domain.RefreshTokenEntity;
import com.multicamp.persistence.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    
    public RefreshTokenEntity findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
    /*
     * 로그인을 할 때 refresh token을 생성해 db에 저장하게 되는데, 이미 존재하는 refresh token이 있다면 해당 토큰은 삭제 시켜주어야 
     * 무분별한 refresh token 생성을 막아 보안성을 높일 수 있다고 생각이 들었다. 
     * 따라서, 로그인할 때 마다 해당 멤버가 가진 refresh token을 모두 지워준 후 새로 생성한 refresh token을 저장한다.
     * */
    @Transactional
    public RefreshTokenEntity addRefreshToken(RefreshTokenEntity entity) {
    	
    	//기존 리프레시토큰 지워주는 로직 들어가야 함/////////////
    	Optional<RefreshTokenEntity> dbrtk=this.refreshTokenRepository.findByUserIdx(entity.getUserIdx());
    	log.info("dbrtk isPresent==={}",dbrtk.isPresent());
    	
    	if(dbrtk.isPresent()) {
    		if(!tokenProvider.validToken(dbrtk.get().getRefreshToken())) {//기존 refreshToken이 유효하지 않다면 삭제
    			this.refreshTokenRepository.delete(dbrtk.get());
    			log.info("****기존 refreshToken이 유효하지 않다면 삭제***********");
    			return refreshTokenRepository.save(entity);
    		}else {
    			log.info("기존 refreshToken토큰 유효한 경우는  기존 토큰으로 인가==================");
    			//entity.update(dbrtk.get());
    			return dbrtk.get();
    		}
    		
    	}else {
    		log.info("DB에 저장된 refreshToken 토큰이 없을 경우 새로운 토큰으로 저장=============");
    		return refreshTokenRepository.save(entity);
    	}
    	
    	
    	// Update를하고 따로저장하는코드가 없는데 jpa의 변경감지 기능을통해서 트랜잭션이 
    	//끝날때 기존에 저장됐던 값이랑 달라지면 자동으로 update 쿼리를 실행해줍니다.
    }
    @Transactional
    public void matches(String refreshToken, Long userIdx) {
        RefreshTokenEntity savedToken = refreshTokenRepository.findByUserIdx(userIdx)        		
                .orElseThrow(InvalidRefreshTokenException::new);
        log.info("matches() db에 저장된 savedRefreshToken={}", savedToken.getRefreshToken());
        log.info("matches() refreshToken={}", refreshToken);
        //유효한 토큰이 아니라면 db에서 회원의 idx로 기존 토큰을 삭제하고 예외 발생
        if (!tokenProvider.validToken(savedToken.getRefreshToken())) {
        	log.info("유효한 토큰이 아닌 경우&&&&");
            refreshTokenRepository.delete(savedToken);
            throw new InvalidRefreshTokenException();
        }
        //토큰은 유효해도 db에서 가져온 토큰값과 일치하는지 여부를 체크해서 일시하지 않으면 예외 발생
        savedToken.validateSameToken(refreshToken);//
//        if(!savedToken.getRefreshToken().equals(refreshToken)) {
//        	throw new InvalidRefreshTokenException();
//        }
    }
    
    
    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken).ifPresent(refreshTokenRepository::delete);
    }
	
}
