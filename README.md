# restboot
- Spring Boot
- Swagger
- Security
  <br><br>
## 핵심 구현 내용
-  회원가입 처리 (비밀번호 암호화 포함)
-  로그인시 role-USER인 경우 로그인 처리 (DATABASE와 연동)
-  사용자별 접근 권한 제어 (일반 USER와 관리자 ADMIN으로 나눠 접근 제어 처리)
<br><br>
## 1. 인증이 필요한 페이지를 요청했을때 처음 만나는 화면 -로그인 화면
![image](https://github.com/swanbaek/restboot/assets/20180958/f51e3817-01d5-4443-8806-dc75e0fa7c64)

<br><br>
## 2. 회원가입
![image](https://github.com/swanbaek/restboot/assets/20180958/acd75895-cdd9-4642-808c-5bf3e9380eea)

<br><br>
## 3. 인증이 필요한 페이지 메뉴
<br>
![image](https://github.com/swanbaek/restboot/assets/20180958/617059a4-4c65-4164-ab72-5219d0b1022b)

<br><br>

### [1] 일반 USER가 각 페이지에 들어갈 경우
<br>
- ![image](https://github.com/swanbaek/restboot/assets/20180958/025c11e3-badb-48e3-96c1-0f8a0715ab30)

<br><br>

### [2] 관리자 ADMIN이 각 페이지에 들어갈 경우
<br>
![image](https://github.com/swanbaek/restboot/assets/20180958/494352e9-e303-42b5-a813-0ba8d0a0f87a)
<br>
![image](https://github.com/swanbaek/restboot/assets/20180958/36ec4e63-75b3-422a-b49e-8001d1d2e0b1)

<br><br>

### [3] MyPage 에 들어갈 경우 - 인증이 필요한 페이지
<br>
![image](https://github.com/swanbaek/restboot/assets/20180958/f099e47f-378a-4016-bf36-e150ea98d71f)

<br><br>




