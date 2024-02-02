### 스프링 MVC 2

<hr />

##### thymeleaf
- 특징
    1. SSR(Server Side Rendering) : 백엔드 서버에서 html을 동적 렌더링
    2. 네츄럴 템플릿 : 순수 html을 최대한 유지하면서 뷰 템플릿도 사용할 수 있는 특징
        - html을 유지 : 웹브라우저에서 절대 경로로 내용 확인 가능 + 서버를 통해 뷰 템플릿을 거치면 동적으로 변경된 결과 확인 가능
    3. 스프링 통합 지원 : 스프링과 자연스럽게 통합 및 다양한 기능의 편리한 사용 지원

- 기능
    1. text, utext
        - 텍스트를 출력하는 기능
        - ```th:text="${data}"``` or [[${data}]]
        - thymeleaf는 escape 자동 적용
        - 전달받은 text에서 escape 해제를 원하는 경우
            - ```th:utext="${data}"``` or [(${data})]
    2. 변수
        - 변수 사용 시에는 변수 표현식, Spring EL 사용
            - 변수 표현식 : ${...}
            - Spring EL
                - Object
                    - ${user.username} = userA
                    - ${user['username']} = userA
                    - ${user.getUsername()} = userA
                - List
                    - ${users[0].username} = userA
                    - ${users[0]['username']} = userA
                    - ${users[0].getUsername()} = userA
                - Map
                    - ${userMap['userA'].username} = userA
                    - ${userMap['userA']['username']} = userA
                    - ${userMap['userA'].getUsername()} = userA
    3. 기본 객체
        - request, response, session, servletContext 객체는 스프링 부트 3.0부터 제공하지 않음
        - ${#locale} : 기본 객체 제공
        - 편의 객체
            - param : http 요청 파라미터 접근
                - ${param.paramData}
            - session : http 세션 접근
                - ${session.sessionData}
            - 스프링 빈 접근
                - ${@helloBean.hello('Spring!')}
    4. link
        - 단순 url
            - th:href="@{/hello}
        - query parameter
            - th:href="@{/hello(param1=${param1}, param2=${param2})}
        - path variable
            - th:href="@{/hello/{param1}/{param2}(param1=${param1}, param2=${param2})}
        - path variable + query parameter
            - th:href="@{/hello/{param1}(param1=${param1}, param2=${param2})}
    5. 리터럴
        - 소스 코드상 고정된 값
        - 작은 따옴표로 감싸야 하나, 공백없이 쭉 이어지는 문자는 토큰으로 인지하므로 감싸지 않아도 됨
        - 종류
            - 문자 : 'hello'
            - 숫자 : '10'
            - 불린 : 'true'
            - null : 'null'
        - 리터럴 대체 문법
            - 공백이 존재하는 문자열에서 작은 따옴표 대신 다음과 같이 사용 가능
            - |hello ${data}|
        