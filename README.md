# WebMailSystem
웹 메일 시스템 유지보수 프로젝트

db 보안문제로 application-db.properties 파일을 gitignore 파일에 등록시켜놓음 그러므로 각자 파일 생성 해야함
사용하는 db에 맞게 application-db.properties 생성후 밑에 내용 적절하게 변경할것
```
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb://localhost:3306/mail
spring.datasource.username=jdbctester
spring.datasource.password=1234
```
Oauth2 보안문제로 application-oauth2.properties 파일을 gitignore 파일에 등록시켜놓음 그러므로 각자 파일 생성 해야함
application-oauth2.properties 생성후 밑에 내용 적절하게 변경할것
cliend id,secret은 https://developers.kakao.com/ 에서 등록후 값 얻어오기
받아오는 정보로 account_email만 선택해야함.
```
#Kakao
spring.security.oauth2.client.registration.kakao.client-id={client id}
spring.security.oauth2.client.registration.kakao.client-secret={secret}
spring.security.oauth2.client.registration.kakao.scope=account_email
spring.security.oauth2.client.registration.kakao.client-name=Kakao
spring.security.oauth2.client.registration.kakao.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.kakao.redirect-uri=http://localhost:8888/webmail/login/oauth2/code/kakao
spring.security.oauth2.client.registration.kakao.client-authentication-method = POST
spring.security.oauth2.client.provider.kakao.authorization-uri=https://kauth.kakao.com/oauth/authorize
spring.security.oauth2.client.provider.kakao.token-uri=https://kauth.kakao.com/oauth/token
spring.security.oauth2.client.provider.kakao.user-info-uri=https://kapi.kakao.com/v2/user/me
spring.security.oauth2.client.provider.kakao.user-name-attribute=id
```