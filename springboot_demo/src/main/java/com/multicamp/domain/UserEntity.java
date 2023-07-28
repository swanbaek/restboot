package com.multicamp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//db table에 상응하는 엔티티 클래스. 엔티티 인스턴스는 테이블의 한 행에 해당함
//ORM이 엔티티를 보고 어떤 테이블의 어떤 필드에 매핑하는지 알 수 있어야 한다
//또 어떤 필드가 PK인지 FK인지 구분할 수 있어야 함. 
//table schema 정보는 JPA어노테이션을 이용해 정의한다
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints= {@UniqueConstraint(columnNames = "nickname")})
public class UserEntity {

	@Id
	private String idx;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String nickname;
	//@Column(nullable = false) ==>OAuth구현시 not null을 주면 SSO구현시 문제가 생김 따라서 null을 허용토록 하자
	private String pwd;
	
	private String role;//사용자의 롤 (ex- USER,ADMIN)
	private String authProvider;//이후 OAuth에서 사용할 유저 정보 제공자: github
}
