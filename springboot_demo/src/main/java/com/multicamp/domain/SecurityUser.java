package com.multicamp.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
/*
- spring security가 제공하는 User 클래스를 우리가 정의한 Member로 사용하기 위해 커스텀합니다.
- 이후 SecurityUser를 통해 Member에 접근할 것이므로 Member를 필드로 갖게 하고 생성자를 통해 값을 유지시켜 줍니다.
- super 키워드를 이용해 부모 클래스(User)의 생성자로 username, password, role 을 넘겨줍니다.
 * */
@Data
public class SecurityUser extends User{
	private UserVO member;
	Logger log=LoggerFactory.getLogger(getClass());
	
	public SecurityUser(UserVO member) {
		super(member.getUserid(), member.getPasswd(), 
				AuthorityUtils.createAuthorityList("ROLE_"+member.getRole().toString()));
		//User를 커스텀화(=>SecurityUser로 사용자정의 클래스를 작성함) 해서 구현할 경우
		//role이름앞에 "ROLE_"를 붙여줘야 각 url패턴별로 사용자 접근 제어가 가능함에 유의하자
		log.info(""+AuthorityUtils.createAuthorityList(member.getRole().toString()));
        log.info("SecurityUser member.username = {}", member.getUserid());
        log.info("SecurityUser member.password = {}", member.getPasswd());
        log.info("SecurityUser member.role = {}", member.getRole().toString());

        this.member = member;
		
	}
}
