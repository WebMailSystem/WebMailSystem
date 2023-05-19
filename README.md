# 웹 메일 시스템 유지보수 프로젝트
# [프로젝트 내용]
주어진 웹 메일 시스템 프로젝트에 대한 <strong>유지보수 단계</strong>를 가정한다.<br/>
유지보수의 유형은 다음과 같다.<br/>
+ <strong>교정(corrective) 유지보수 </strong><p>기존 코드의 오류를 수정하는 유지보수</p>
+ <strong>적응(adaptive) 유지보수</strong><p>새로운 환경 변화에 적응하는 유지보수</p>
+ <strong>완전화(perfective) 유지보수</strong><p>새로운 기능 추가, 변경을 위한 유지보수</p>
+ <strong>예방(preventive) 유지보수</strong><p>유지보수성, 신뢰성 향상을 위한 구조 변경(리팩토링)과 같은 유지보수</p>
---
# [개발 환경]
+ IDE : Apache-NetBeans (17)
+ JDK : zulu 11
+ OS : Window 10
+ DB : MariaDB 10.11.2
---
# [ 코딩 규칙 ]
## 1. 명명 규칙 (★★★)
+ 패키지 이름 : 소문자 ex) dto, entity, controller <br>
+ 클래스 이름 : 대문자로 시작하면서 명사 (파스칼 표기법 사용) ex) LoginController <br>
+ 메소드 이름 : 소문자로 시작하면서 동사 (카멜 표기법 사용) ex) create, update
+ 변수 이름 : 소문자로 시작하면서 명사 (카멜 표기법 사용) ex) name
+ 불리언 변수, 메소드 이름 : 변수 또는 메소드 이름 앞에 is ex) isAnimal
+ 검색 메소드 : 이름 앞에 find ex) findById

## 2. 문장 규칙
+ 문장은 가급적이면 한 줄에 작성하기<br>
```java
// 잘 된 예시
Article article = articleRepository.findById(articleId).orElseThrow(null);
```

+ 변수는 public 선언보다는 private 선언하기

## 3. 주석 규칙 (★★★★★)
+ 헤더 주석
```java
/**
 * 클래스 기능 설명 1~3줄 작성
 * 
 *  @author 개발자 
 */
@Controller
public class Controller {

}
```

+ 블록 주석
```java
@Controller
public class Controller {
    /* 
        메서드 기능 작성하기
    */
    public String index() {
        return index; // 한 줄 주석은 필요로 할 때 사용하기
    }
}
```

+ 명확하고 간결하게 작성하기, 하나의 책임만 가지고 있는 것이 아닌 것 같으면 분리 고려하기

+ 불필요한 주석 제거하기

## 4. 레이아웃 규칙
+ 코드 띄어쓰기 및 들여쓰기<br>
```java
// 반복문 사용 규칙

// 잘 된 예시 - 우리가 사용할 규칙
for (int i = 0; i < 10; i++) {
    if (i == 0) {
        break;
    }
}


// 잘못된 예시
for(int i=0;i<10;i++){
    if(i=0){
        break;
    }
}
```
```java
// if문 사용 규칙
if (i > 90) {
    // 90점 이상
} else if (i > 80) {
    // 80점 이상
} else {
    // 80점 미만
}
```


# [ 형상 관리 원칙 ]
작성 해야함


# [ 프로젝트 설정 파일 ]
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

