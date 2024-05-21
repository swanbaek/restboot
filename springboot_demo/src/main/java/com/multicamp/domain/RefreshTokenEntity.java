package com.multicamp.domain;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.multicamp.cmm.exception.InvalidRefreshTokenException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Entity 
@Table(name="refresh_token")
@SequenceGenerator(name="refresh_token_SEQ_GEN",//시퀀스 제너레이터 이름
sequenceName = "refresh_token_SEQ", //시퀀스명
initialValue = 1, //시작값
allocationSize = 1)//메모리 통해 할당할 범위 사이즈
public class RefreshToken {
	
	@Id
    
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_SEQ_GEN")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_idx", nullable = false, unique = true)
    private Long userIdx;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
    
    @Column(name = "expirydate", nullable = false)
    private Date expiryDate;

    public RefreshToken(Long userIdx, String refreshToken, Duration expiry) {
    	
        this.userIdx = userIdx;
        this.refreshToken = refreshToken;
        Date today = new Date(); //sysdate를 디폴트로 주지 않았을 경우는 시스템의 현재 날짜를 설정해서 넘기자.
        long time=today.getTime()+expiry.toMillis();
        this.expiryDate=new Date(time);
    }
    /*
    @PrePersist
    public void prePersist() {
        Date today = new Date(); //sysdate를 디폴트로 주지 않았을 경우는 시스템의 현재 날짜를 설정해서 넘기자.
        long time=today.getTime()+Duration.ofDays(1).toMillis();
        this.expiryDate=new Date(time);
    }
	*/
    public RefreshToken update(RefreshToken newRefreshToken) {
        this.refreshToken = newRefreshToken.refreshToken;
        this.id=newRefreshToken.id;
        this.expiryDate=newRefreshToken.expiryDate;
        return this;
    }
    //참고: https://easthshin.tistory.com/13
    public void validateSameToken(String token) {
        if (!this.refreshToken.equals(token)) {
        	System.out.println("savedToken과 refershToken이 일치하지 않아 예외를 발생시킴");
            throw new InvalidRefreshTokenException();
        }
    }

}
