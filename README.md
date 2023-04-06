# WebMailSystem
웹 메일 시스템 유지보수 프로젝트


db 보안문제로 application-db.properties 파일을 gitignore 파일에 등록시켜놓음 그러므로 각자 파일 생성 해야함
사용하는 db에 맞게 application-db.properties 생성후 밑에 내용 적절하게 변경할것
`
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb://localhost:3306/mail
spring.datasource.username=jdbctester
spring.datasource.password=1234
`
