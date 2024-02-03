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
    6. 연산
        - 연산은 자바 문법과 비슷
        - HTML 엔티티 사용(<, >, 등)되는 부분에 주의
        - Elvis 연산자
            - ?:가 엘비스처럼 생겼다고 엘비스 연산자 ㅋㅋㅋ
            - 삼항 연산자랑 비슷
            - th:text="${data}?: '데이터가 없습니다.'
                - data에 값이 있으면 해당 데이터 출력, 없으면 '데이터가 없습니다.' 출력
        - No operation
            - ```<span th:text="${data}?: _">데이터가 없습니다.</span>```
            - data에 값이 없으면 타임리프 연산을 수행하지 않음 -> 데이터가 없습니다. 출력
    7. 타임리프 속성 값 설정
        - th:* 로 속성을 적용하면 기존 속성을 대체, 없는 속성이면 새로 만듦
        - 속성 치환
            - name="mock" th:name="user" -> 타임리프를 거치면 name 속성이 user로 치환됨
        - 속성 추가
            - th:attrappend
                - 해당 속성의 뒤에 추가
                - class="text" th:attrappend="class=' large'" -> 결과 class="text large"
            - th:attrprepend
                - 해당 속성의 앞에 추가
                - class="text" th:attrprepend="class='large '" -> 결과 class="large text"
            - th:classapeend
                - 띄어쓰기 안해도 알아서 처리해서 뒤에 붙여줌
                - class="text" th:classappend="class='large'" -> 결과 class="text large"
        - checked 처리
            - html checked 속성은 check라는 속성만 있어도(=값을 false로 해도) 체크 처리
            - th:checked를 false로 하면 checked 속성을 아예 안넣음
    8. 반복
        - th:each로 사용
        - ```<tr th:each="user : ${users}">```
        - 두번째 파라미터로 반복 상태에 대한 정보 받을 수 있음
        - 생략하고 지정 변수명 + Stat(네이밍 룰)로 받을 수 있음
        - ```<tr th:each="user , userStat : ${users}">``` -> userStat에 반복 상태 정보
            - index : 0부터 시작하는 순서 값
            - count : 1부터 시작하는 순서 값
            - size : 전체 사이즈
            - event, odd : 홀수, 짝수 여부를 boolean으로
            - first, last : 처음, 마지막 여부를 boolean으로
            - current : 현재 객체
    9. 조건부 평가
        - if, unless : if-else와 유사
            - 조건 불만족하면 해당 태그 전체 날림
        - switch
            - switch 문이랑 동일한 구조
            - ```* == default```
    10. 주석
        1. html 기본 주석
            - ```<!-- 내용... -->```
        2. 타임리프 파서 주석
            - ``` <!--/* 내용... */--> ```
            - 타임리프 파서 차원에서 주석 처리 -> 안나옴
        3. 타임리프 프로토타입 주석
            - ``` <!--/*/ 내용... /*/--> ```
            - html 파일로는 주석처리
            - 타임리프로 랜더링 되면 화면에 표출
    11. 블록
        - 타임리프의 자체 태그
        - ```<th:block>```
        - 블록 태그 내부의 요소 모두 Loop를 돌리고 싶을 때 등 일반적인 반복으로 해결하기 어려운 경우 사용
        - 랜더링 시에는 사라짐
    12. 자바스크립트 인라인
        - 자바스크립트에서 타임리프를 편하게 사용하기 위한 기능
        - ```<script th:inline="javascript">```
        - 사용하지 않는 경우, 자바스크립트의 문법에 맞춰 전달되는 타임리프 값을 바꿔줘야 하는데, 이러한 부분을 알아서 처리해줌
        - 문제가 될 수 있는 문자 포함하는 경우 자동 자바 스크립트에 맞게 escape 처리 : ex) " -> /"
        ```
        <!-- 자바스크립트 인라인 사용 전 -->
        <script>
        var username = UserA; // 문자로 처리되지 않음 -> 에러
        var age = 10;

        //자바스크립트 내추럴 템플릿
        var username2 = /*UserA*/ "test username"; // 타임리프 랜더링 안됨

        //객체
        var user = BasicController.User(username=UserA, age=10); // 객체를 toString()으로 처리 -> 에러
        </script>

        <!-- 자바스크립트 인라인 사용 후 -->
        <script>
        var username = "UserA"; // 문자로 처리
        var age = 10;

        //자바스크립트 내추럴 템플릿
        var username2 = "UserA"; // 타임리프 랜더링돼서 주석에 있는 값으로 랜더링

        //객체
        var user = {"username":"UserA","age":10}; // 객체를 json으로 처리
        </script>
        ```
        - 자바스크립트 인라인 each
            - script 내에서 loop를 돌리고 싶을때
                ```
                <script th:inline="javascript">

                [# th:each="user, stat : ${users}"]
                var user[[${stat.count}]] = [[${user}]];
                [/]

                </script>
                ```
                결과
                ```
                <script>

  
                var user1 = {"username":"UserA","age":10};
                var user2 = {"username":"UserB","age":20};
                var user3 = {"username":"UserC","age":30};
  

                </script>
                ```
    13. 템플릿 조각
        - header, footer 등 공통으로 사용되는 부분을 템플릿 조각으로 만들고 불러와 사용 가능
        - ```th:fragment="조각이름"```으로 템플릿 조각 지정
        - th:insert, th:replace로 불러와서 사용
            - th:insert="~{템플릿조각경로(template/fragment/footer) :: fragment이름}" : 해당 속성이 적용된 태그 내부에 템플릿 조각 삽임
            - th:replace="~{템플릿조각경로(template/fragment/footer) :: fragment이름}" : 해당 속성이 적용된 태그를 템플릿 조각으로 대체 -> 속성이 적용된 태그는 사라짐
        - 파라미터 사용
            - fragment 이름에 괄호로 파라미터 포함 가능
                - th:fragment="조각이름 (파라미터이름, 파라미터이름)"
                - th:replace="~{템플릿조각경로(template/fragment/footer) :: fragment이름 (파라미터1, 파라미터2)}"
        
