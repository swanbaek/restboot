# restboot
- Spring Boot
- Swagger
- React,Redux
- JPA
- JWT
  
## 핵심 구현 내용 (세션을 이용하지 않는 로그인 처리)
-  회원가입 처리 (비밀번호 암호화 포함)-react와 JPA 연동 (Security 포함)
-  로그인시 인증받은 회원일 경우 secret key 서명과 함께 token을 발행 토큰과 닉네임(아이디)을 포함한 응답을 보낸다
-  클라이언트쪽에서는 ACCESS_TOKEN이란 키값으로 서버가 발행한 토큰을 localStorage에 저장한다
-  클라이언트는 요청을 보낼때 ACCESS_TOKEN을 꺼내 요청 헤더에 Authorization이라는 키값에 "Bearer 토큰값"을 포함하여 서버에 보낸다
-  요청을 받은 서버쪽에서는 해당 요청을 처리하기 전에 JwtAuthenticationFilter 가  Bearer토큰을 파싱하여
    secret key 서명과 함께 유효한 토큰인지, 위조된 토큰은 아닌지 검증하여 이를 통과하면 로그인한 유저로 처리한다
