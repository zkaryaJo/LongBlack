# LongBlack
### 환경
1. Java 17
2. Springboot v3.3.1
3. DB : JPA, H2 (메모리DB 설정으로 서버 재시작 시 데이터가 초기화 됩니다.)
4. JWT 토큰 사용
5. REST API 문서화를 위해 Spring REST Docs 및 관련 플러그인, 라이브러리들을 사용예정

### 플러그인
```
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.3.1'        // Springboot v3.3.1
	id 'io.spring.dependency-management' version '1.1.5'
  id 'org.asciidoctor.jvm.convert' version '3.3.2'
	id 'com.epages.restdocs-api-spec' version '0.18.2'   //REST API 문서 자동화를 위한 플러그인 (restdocs to open api spec)
	id 'org.hidetake.swagger.generator' version '2.18.2' //REST API 문서 자동화를 위한 플러그인 (Swagger ui 생성)
}
```

