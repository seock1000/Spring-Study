# 스프링 MVC 1편

---

##### 웹서버

- HTTP 기반 동작
- 정적 리소스(html, css, js, 이미지, 영상) 제공 + 기타 부가기능
- nginx, apache

##### 웹 어플리케이션 서버(WAS)

- HTTP 기반 동작
- 웹서버 기능 + 프로그램 코드를 실행하여 로직 수행
    - 동적 html, HTTP API(JSON)
    - 서블릿, JSP, 스프링 MVC
- Tomcat, Jetty, Undertow

##### 웹서버 vs. WAS

- 웹서버는 정적 리소스, WAS는 어플리케이션 로직
- 그러나, 최근에는 경계가 모호
    - 웹서버도 플러그인을 통해 프로그램 실행하는 경우 O
    - WAS도 웹서버의 기능 제공
- 자바는 보통 서블릿 컨테이너 기능을 제공하면 WAS
    - 그러나, 서블릿 없이 자바코드를 실행하는 서버 프레임워크도 존재
- **WAS는 애플리케이션 코드 실행에 특화**

##### 웹 시스템 구성
- WAS, DB 만으로 구성
    - WAS는 정적 리소스와 애플리케이션 로직 모두 제공 가능하므로
    - 단점
        - WAS가 너무 많은 역할 -> 서버 과부하 우려
        - 비싼 애플리케이션 로직(실제 중요한 비즈니스 로직)이 정적 리소스로 인해 수행이 어려울 수 있음
        - WAS 장애시 오류 화면 노출 불가 -> 접근 자체가 안되니까
- 웹서버 - WAS - DB
    - 일반적으로 구성하는 시스템
    - 정적 리소스는 웹서버가 처리
    - 웹서버는 동적인 로직 처리가 필요하면 WAS에 요청 위임
    - WAS는 중요한 애플리케이션 로직 처리 전담
    - 장점
        - 효율적인 리소스 관리
        - 웹서버는 잘 죽지 않음(단순) + WAS는 잘 죽음(복잡)
            - WAS, DB 장애시 웹서버가 오류 화면 제공 가능


##### 서블릿
- urlPatterns의 URL이 호출되면 서블릿 코드 실행
- HTTP 메세지를 처리하기 쉽게 사용할 수 있는 객체
    - 요청 : HttpServletRequest
    - 응답 : HttpServletResponse
    - HTTP 스펙을 편리하게 사용 가능
- HTTP 요청, 응답 흐름
    - HTTP 요청시
        1. WAS는 Request, Response 객체를 새로 만들어 서블릿 객체 호출
        2. 개발자는 Request 객체에서 HTTP 요청 정보를 편리하게 꺼내 사용
        3. Response 객체에 HTTP 응답 정보를 편리하게 입력
        4. WAS는 Response 객체에 담긴 내용으로 HTTP 응답 정보 생성
- 서블릿 컨테이너
    - 서블릿 객체의 생명주기를 관리하는 컨테이너
    - 톰캣과 같이 서블릿을 지원하는 WAS
    - 싱글톤으로 관리
        - 최초 로딩 시점에 서블릿 객체를 미리 만들어두고 재활용
        - 모든 고객 요청이 동일한 서블릿 객체 인스턴스에 접근
            - HTTP Request, Response 객체는 요청마다 새로 생성 -> 요청마다 고객이 다 다르니까!
        - 공유 변수 사용 주의
    - 동시 요청을 위한 멀티 쓰레드 처리 지원
- **멀티 쓰레드**
    - 서블릿 객체는 누가 호출?
        - 쓰레드가 호출
        - 쓰레드는 한 번에 하나의 코드 라인만 수행
    - 처리 방식
        - 요청마다 쓰레드 생성
            - 장점
                - 동시요청 처리 가능
                - 리소스가 허용하는 만큼 처리 가능
                - 하나의 쓰레드가 지연 되어도 나머지 쓰레드가 동작
            - 단점
                - 쓰레드의 비싼 생성 비용
                - 컨텍스트 스위칭(쓰레드를 전환) 비용
                - 쓰레드 생성에 제한이 없으므로, CPU나 메모리의 임계점을 넘으면 서버가 죽을 수 있음
        - 따라서, 대부분 쓰레드 풀을 사용
            - 요청마다 쓰레드 생성 방식의 단점 보완
            - 쓰레드 풀에 미리 사용할 쓰레드들을 만들어 둠
            - 요청이 오면 쓰레드 풀의 쓰레드를 사용하고 반환 + 모든 쓰레드가 사용 중이면 대기 or 거절
            - 톰캣은 default 200개
            - 장점
                - 쓰레드 미리 생성 : 쓰레드 생성, 종료 비용 절약 -> 응답시간 빠름
                - 생성 가능한 쓰레드 최대치가 존재 : 많은 요청에도 기존 요청은 안전하게 처리 가능
            - 실무 팁
                - WAS의 주요 튜닝 포인트 == 최대 쓰레드 수
                    - 너무 낮으면, CPU 활용률은 낮고 많은 요청 처리 어려움
                    - 너무 높으면, 임계점 초과 시 서버 다운
                - 클라우드면, 일단 서버 늘리고 추후 튜닝, 클라우드 아니면 열심히 튜닝
                - 적정 숫자 찾기
                    - 애플리케이션 로직의 복잡도, CPU, 메모리, IO 리소스 상황에 따라 모두 상이
                    - 성능테스트!
                        - 실제 서비스와 유사하게 테스트 시도
                        - 툴 : 아파치, 제이미터, nGrinder
    - 멀티 쓰레드 부분은 WAS가 처리 -> 개발자는 멀티 쓰레드 관련 코드 신경 안써도 됨 -> 개발 생산성 높아짐
        - 다만, 멀티 쓰레드 환경이므로 싱글톤 객체를 주의해서 사용!

##### HTML, HTTP API
- HTML
    - Web server에서 정적인 HTML, css, js, 이미지, 영상 등 파일을 제공
    - WAS에서 동적으로 필요한 HTML 파일을 생성해서 전달
- HTTP API
    - HTML이 아닌 데이터를 전달
    - 주로 JSON 형식
    - 다양한 시스템에서 호출(웹, 앱, server to server)
    - 필요한 UI 화면은 클라이언트(웹, 앱)가 별도 처리

- SSR(server side rendering)
    - 서버에서 최종 HTML을 생성해서 클라이언트에 전달
    - 주로 정적이고 복잡하지 않은 화면에 사용
    - 관련 기술 : JSP, 타임리프 등
    - 백엔드 개발자는 학습 필수

- CSR(client side rendering)
    - HTML 결과를 자바스크립트를 사용해 웹 브라우저에서 동적으로 생성해서 적용
    - 복잡하고 동적인 화면에 사용
    - 웹 환경을 앱처럼 필요한 부분부분 변경 가능
    - ex) 구글 맵, Gamil, 구글 캘린더
    - 관련 기술 : React, Vue.js 등
    - 웹 프론트엔드 개발자의 분야

##### 자바 웹기술 역사
1. 서블릿
    - HTML 생성이 어려움
2. JSP
    - HTML 생성은 편리하나, 비즈니스 로직까지 너무 많은 역할 담당
3. 서블릿, JSP 조합 MVC 패턴 사용
    - 모델, 뷰, 컨트롤러로 역할 나눠 개발
    - 관심사를 분리 -> 화면에 관한 부분 : 뷰
4. 수 많은 MVC 프레임워크 등장
    - MVC 패턴 자동화, 복잡한 웹 기술을 편리하게 사용할 수 있는 다양한 기능 지원
    - 스트럿츠, 웹워크, 스프링 MVC(과거 버전)
5. 애노테이션 기반 스프링 MVC 등장
    - @Controller
    - 애노테이션 기반 : 유연, 편리하게 깔끔한 코드로 작성 가능
6. 스프링 부트 등장
    - 서버 내장 - 톰캣
        - 과거에는 서버에 WAS를 설치하고, 소스는 war 파일을 만들어서 설치한 WAS에 배포
        - 스프링 부트는 빌드 결과(jar)에 WAS 서버 포함 -> 빌드 배포 단순화
7. 최신
    - 스프링 웹 기술 분화
        - Web Servlet - Spring MVC
            - 서블릿 기반
        - Web Reactive - Spring WebFlux
            - netty로 구현

            - 장점
                - 비동기 논블로킹 처리
                - 최소 쓰레드 + 최대 성능 : 컨텍스트 스위칭 비용 효율화
                - 함수형 스타일 개발 : 동시처리 코드 효율화
                - 서블릿 기술 사용 X

            - 단점
                - 높은 기술적 난이도 
                - RDB 지원 부족 등

##### 자바 뷰 템플릿 역사
- 뷰 템플릿 : HTML(주로!)을 편리하게 생성하는 기능
1. JSP
    - 느린 속도, 부족한 기능
2. 프리마커, 벨로시티
    - 속도 문제 해결, 다양한 기능
3. 타임리프(Thymeleaf)
    - 내추럴 템플릿 : HTML의 모양 유지 + 뷰 템플릿 적용 가능
    - 스프링 MVC와 강력한 기능 통합
    - 성능은 프리마커, 벨로시티가 더 빠르나, 스프링을 쓴다면 타임리프 추천!


##### HttpServletRequest

- 역할
    - Http 요청 메세지를 직접 파싱해서 사용해도 되지만 불편
    - 서블릿은 Http 요청 메세지를 편리하게 사용할 수 있게 메세지를 파싱하여 결과를 HttpServletRequest 객체에 담아 제공
- 부가 기능
    - 임시 저장소 기능
        - HTTP 요청의 시작부터 끝까지 유지
        - 저장 : request.setAttribute(name, value)
        - 조회 : request.getAttribute(name)
    - 세션 관리 기능
        - request.getSession(create: true)
- 중요 포인트!
    - HttpServletRequest, HttpServletResponse는 결국 HTTP 메세지를 편리하게 사용하도록 도와주는 객체!
    - 따라서, HTTP 메세지를 먼저 이해하고 사용하는게 중요!

##### HTTP 요청 데이터
- HTTP 요청 메세지를 통해 서버로 데이터를 전달하는 3가지 방법

    1. GET + 쿼리 파라미터
        - 예시 : /url?username=hello&age=20
        - 메세지 바디 없이 URL에 쿼리 파라미터를 포함하여 전달
        - 검색 필터, 페이징 등에서 주로 사용
        - HttpServletRequest가 제공하는 메서드를 통해 쿼리 파라미터 조회 가능
    2. POST + HTML Form
        - content-type : application/x-wwww-form-urlencoded
        - 메시지 바디에 쿼리 파라미터 형식으로 전달 : username=hello&age=20
            - 형식이 쿼리 파라미터와 똑같기 때문에 쿼리 파라미터를 꺼내는 메서드(request.getParameter)로 꺼낼 수 있음
            - 클라이언트 입장에선 방식에 차이가 있지만, 서버에선 둘의 형식이 동일
        - 회원가입, 상품 주문 등에 주로 사용
        - HTTP message body에 데이터를 담아서 요청
            - HTTP API에 주로 사용
            - JSON, XML, TEXT 등의 정보를 담아 요청
            - 데이터 형식은 주로 JSON 사용
            - POST, PUT, PATCH 등 사용

##### HttpServletResoponse 
- 역할
    1. HTTP 응답 메세지 생성
        - HTTP 응답코드 지정
        - 헤더, 바디 생성
    2. 편의기능 제공
        - Content-Type
        - 쿠키
        - Redirect

##### HTTP 응답 데이터

- HTTP 응답 메세지는 주로 다음의 내용을 담아 전달
    1. 단순 텍스트
        - content-type을 text/plain으로 지정
    2. HTML
        - content-type을 text/html로 지정
    3. HTTP API - Message body : JSON


##### 템플릿 엔진
- 서블릿과 자바 코드 만으로 HTML을 생성하는 것이 가능하지만, 복잡하고 비효율적
    - 자바 코드에 HTML 생성 작업이 포함돼서
- HTML 문서에서 필요한 곳만 코드를 적용하는 것이 더 편리
- 때문에 템플릿 엔진 등장
- 종류
    - JSP, Thymeleaf, Freemarker, Velocity 등

##### JSP
- build.gradle 추가
    ```
        implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'
        implementation 'jakarta.servlet:jakarta.servlet-api' // 스프링부트 3.0 이상
        implementation 'jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api' // 스프링부트 3.0 이상
        implementation 'org.glassfish.web:jakarta.servlet.jsp.jstl' // 스프링부트 3.0 이상
    ```
- 자바 코드 사용 가능
    - <% ~ %> 부분에 자바 코드 사용
- 한계
    - view와 비즈니스 로직이 섞여 있음
        - 파일이 커지고 유지보수 어려움
        - JSP에 너무 많은 역할

##### MVC 패턴
- 서블릿, JSP의 한계
    - 하나의 서블릿이나 JSP 만으로 비즈니스 로직과 렌더링까지 모두 처리
        - 너무 많은 역할 + 유지보수의 어려움
    - 변경의 라이프 사이클
        - 분리에서 변경 주기를 고려하는 것이 좋음
        - UI와 비즈니스 로직의 수정은 각각 다르게 발생하는 경우가 많고 대게 서로에게 영향을 주지 않음
    - 기능 특화
        - JSP와 같은 뷰 템플릿은 화면의 렌더링에 최적화 되어 있기 때문에 해당 부분의 업무만 담당하는 것이 효과적

- MVC 패턴
    - 하나의 서블릿이나 JSP로 처리하던 것을 Controller와 View 영역으로 역할을 나눈 패턴
    - 컨트롤러 : HTTP 요청을 받아 파라미터를 검증 + 비즈니스 로직 실행 + 뷰에 전달할 결과 데이터를 모델에 담아 뷰에 전달
    - 모델 : 뷰에 출력할 데이터를 담아둠
    - 뷰 : 모델에 담겨있는 데이터를 사용하여 화면을 그리는 일에 집중(HTML 생성 등)
    - WEB-INF 하위 자원은 컨트롤러를 거쳐 내부에서 forward를 통해서만 조회 가능
    - 한계
        - 중복 코드 발생 - forward
            - View로 이동하기 위한 코드가 중복 호출됨
        - ViewPath에 중복
            - 더해서 다른 뷰로 변경한다면 전체코드가 변경됨
        - 공통처리의 어려움
            - 공통기능을 메서드로 뽑더라도 해당 메서드를 항상 호출해야 함
        - 프론트 컨트롤러 패턴으로 문제 해결 가능
    - **프론트 컨트롤러 패턴**
        - 프론트 컨트롤러가 없으면, 각 컨트롤러에 공통 로직 포함
        - 공통 로직을 프론트 컨트롤러에서 처리하는 수문장(프론트 컨트롤러)을 두는 패턴
        - 스프링 웹 MVC의 핵심
        - 특징
            - 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청 받음
            - 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아 호출
            - 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿 사용 필요 X

##### 스프링 MVC
    - 구조
        1. DispatcherServlet
            - 스프링 MVC의 프론트 컨트롤러
            - 부모 클래스에서 HttpServlet을 상속받아 사용
            - 스프링 부트는 DispatcherServlet을 서블릿으로 자동으로 등록하며 모든 경로(urlPatterns="/")에 대해 매핑 (더 자세한 경로의 우선순위가 높으므로, 기존에 등록한 서블릿도 함께 동작)
            - 요청 흐름
                1. 서블릿 호출 -> HttpServlet이 제공하는 service() 메서드 호출
                2. DispatcherServlet의 부모인 FrameworkServlet에 service() 오버라이드
                3. FrameworkServle.service()를 시작으로 여러 메서드 호출되면서 DispatcherServlet.doDispatch() 호출
                    - doDispatch() : 핸들러 조회 -> 핸들러 어댑터 조회 -> 핸들러 어댑터를 통해 핸들러 실행 -> ModelAndView 반환 ->  reder() 호출 및 뷰 렌더링
        2. HandlerMapping
            - 요청 url에 매핑된 핸들러 정보 저장
        3. HandlerAdapter
            - 핸들러 실행을 위한 어댑터
            - 핸들러를 호출하고 핸들러가 반환하는 정보를 ModelAndView로 변환하여 반환
        4. ModelAndView
            - 데이터(Model)와 뷰 정보(View)를 담는 객체
        5. ViewResolver
        6. View

##### HTTP 메세지 컨버터
    - @ResponseBody 사용 시
        - HTTP body에 문자 내용 직접 반환
        - viewResolver 대신 HttpMessageConverter 동작
            - 기본 문자 처리 : StringHttpMessageConverter 동작
            - 기본 객체 처리 : MappingJackson2HttpMessageConverter 동작
            - 기타 byte 처리 등을 위한 컨버터도 기본 등록되어 있음
            - 응답은 클라이언트의 HTTP Accept 헤더와 서버의 컨트롤러 반환 타입 정보 등을 조합하여 선택
    - 스프링 MVC는 다음의 경우 HTTP 메세지 컨버터 적용
        - 요청 : @RequestBody, HttpEntity(RequestEntity)
        - 응답 : @ResponseBody, HttpEntity(ResponseEntity)
        - HTTP 요청, 응답 둘 다 사용 => 양방향 사용
            - canRead(), canWrite() : 메서드로 해당 클래스타입 + 미디어타입 지원하는지 체크
            - read(), write() : 실제 read, write 메서드
    - 어노테이션 기반 핸들러(@RequestMapping)를 처리하는 핸들러 어댑터(RequestMappingHandlerAdapter)를 통해 작동
        - ArgumentResolver
            - 어노테이션 기반 컨트롤러는 다양한 파라미터(HttpServletRequest, Model, @RequestParam, @ModelAttribute, @ReqeustBody 등)
            - RequestMappingHandlerAdapter가 ArgumentResolver를 호춣하여 파라미터의 객체를 생성하고, 파라미터로 넘길 객체가 세팅하여 컨트롤러를 호출
        - ReturnValueHandler
            - String, ModelAndView, @ResponseBody, ResponseEntity 등 응답 값을 변환하고 반환
        - ArgumentResolver와 ReturnValueHandler가 HTTP 메세지 컨버터를 호출하여 사용
            - 즉, 추가적으로 HTTP 메세지의 변환이 필요할 때 호출하는 녀석이 HTTP 메세지 컨버터!
            - 요청 : @RequestBody를 처리하는 ArgumentResolver, HttpEntity를 처리하는 ArgumentResolver
            - 응답 : @ResponseBody와 HttpEntity를 처리하는 ReturnValueHandler