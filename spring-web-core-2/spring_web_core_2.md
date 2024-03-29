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

##### 타임리프 스프링 통합
- 스프링 통합으로 추가되는 기능
    - SpringEL 문법 통합
    - 스프링 빈 호출 지원
    - 폼 관리 추가 속성
        - th:object :  기능 강화, 폼 커맨드 객체 선택
        - th:field, th:errors, th:errorclass
    - 폼 컴포넌트 기능
        - checkbox, radio button, List 등 편리하게 사용할 수 있는 기능 지원
    - 스프링 메시지, 국제화 기능의 편리한 통함
    - 스프링 검증, 오류 처리 통합
    - 스프링 변환 서비스 통합
    - 타임리프 템플릿 엔진, 뷰 리졸버 등 빈 등록 절차는 gardle을 통해 라이브러리를 다운로드 받고, 스프링 부트가 빈으로 자동 등록
    - 타임리프 설정 변경 필요시 -> application.properties에서 설정 가능

##### 메세지 국제화

- 메시지 기능 : 여러 화면에 공통되는 텍스트를 관리하는 기능
    - 여러 화면에 공통으로 포함되는 특정 텍스트를 불러서 사용할 수 있게 함
    - 변경을 편리하게 관리
    - message.properties와 같은 메세지 관리용 파일을 만들고 key값으로 불러서 각 HTML에서 사용
- 국제화
    - 나라별로 관리하여 서비스 국제화 가능
    - messages_en.propeties, messages_ko.propeties와 같이 언어별로 파일을 생성하여 관리
    - accept-language 헤더, 사용자가 직접 언어 선택 또는 쿠키 등을 사용하여 처리
    - 스프링은 기본적으로 메시지와 국제화 기능 제공! 타임리프도 해당 기능을 통합하여 제공!


##### Validation

- BindingResult
    - 스프링이 제공하는 검증 오류 보관 객체 -> 검증 오류가 발생하면 해당 객체에 보관
        - ex)  bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다."));
    - BindingResult는 검증 대상의 파라미터의 순서 상 바로 뒤에 위치시켜야 함
    - BidningResult 결과는 model에 자동으로 포함돼서 넘어감
    - ModelAttribute에 바인딩 시 type 오류 발생 시 (integer에 문자값 등)
        - BindingResult가 없으면 오류페이지 출력
        - BindingResult가 있으면 오류 정보를 BindingResult에 담아서 컨트롤러 정상 호출 -> 개발자가 뭔 생각이 있겠구나~ 하면서
    - BindingResult 검증 오류 적용 3가지 방법
        1. @ModelAttribute의 객체에 타입 오류 등 바인딩 실패시 스프링이 FieldError 생성하여 BindingResult에 등록
        2. 비즈니스 에러 등을 개발자가 직접 bindingError.addError()메서드로 등록
        3. Validator 사용
    - Errors 인터페이스를 상속받는 인터페이스로 Errors를 사용해도 되지만, Errors는 단순한 오류 저장, 조회 기능 제공하므로 기능이 좀 부족

- FieldError
    - 생성자
        - rejectedValue가 포함된 생성자와 포함되지 않은 생성자로 두 개의 생성자 제공
        - 파라미터 목록
            1. objectName : 오류가 발생한 객체 이름(requried)
            2. field : 오류 필드(requried)
            3. rejectedValue : 사용자가 입력한 거절된 값
            4. bindingFailure : 타입 오류와 같은 바인딩 실패인지, 검증 실패인지의 구분 값
            5. codes : 메시지 코드
            6. arguments : 메시지에서 사용하는 인자
            7. defaultMessage : 기본 오류 메시지(requried)
    - 사용자 입력값 유지
        - type error로 binding 실패 시
            - type이 틀렸기 때문에 Model에 저장이 불가능
            - 스프링이 binding 실패하는 때에 BindingResult에 FieldError 생성하여 등록하여 view에 사용자 입력값 유지
        - 검증 실패 시
            - FieldError가 제공하는 사용자 입력 값 저장 기능(rejectedValue에 저장)을 사용하여 view에 입력 값 유지
    
    - thymeleaf가 입력 값을 유지하는 매커니즘
        - th:field : 정상 상황에는 모델 객체의 값을 사용, 오류 발생 시 FieldError에서 보관한 값을 사용하여 출력
    - codes, arguments
        - 오류 발생시 오류 코드로 메시지 탐색 가능
        - 오류 코드를 중앙화하여 관리
        - codes : properties 파일에 선언된 경로 String 배열로 기입
        - arguments : 에러 메세지에 파라미터로 전달되는 값 Object 배열로 기입

###### MessageCodesResolver
    ```
        ## 상세한 에러 코드
        required.item.itemName=상품 이름은 필수입니다.
        range.item.price=가격은 {0} ~ {1} 까지 허용입니다.
        max.item.quantity=수량은 최대 {0} 까지 허용합니다.
        totalPriceMin=가격 * 수량의 합은 {0}원 이상이어야 합니다. 현재 값 = {1}

        ## 범용성 있는 에러 코드 (오류 코드의 상세함에 단계를 줄 수 있음)
        required=필수 값 입니다.
        range=범위는 {0} ~ {1} 까지 허용합니다.
        max=최대 {0} 까지 허용합니다.
    ```

    - 상세한 에러 코드(객체명과 필드명을 조합한 메시지)가 있는지 먼저 탐색 후, 없으면 범용성 있는 에러 코드 사용
    - 스프링은 MessageCodesResolver를 통해 이러한 기능 지원
    - 검증 오류 코드로 메시지 코드들을 생성
        - 객체오류 메시지 생성 규칙
            - error code + "." + object name
            - error code
        - 필드 오류 메시지 생성 규칙
            - error code + "." + object name + "." + field
            - error code + "." + field
            - error code + "." + field type
            - error code
        - 즉, 복잡한 메시지부터 단순한 메시지 순서로 검토 -> 복잡할수록 우선순위가 높음
    
    - reject(), rejectValue는 내부에서 MessageCodesResolver를 사용 -> 메시지 코드 생성
    - FieldError, ObjectError의 생성자를 보면 여러 개의 에러 코드를 가질 수 있음 -> 생성된 순서대로 오류 코드를 보관

    - 오류 코드 관리 전략
        - MessageCodeResolver는 required.item.itemName 처럼 구체적인 것을 먼저 만들고, required 처럼 덜 구체적인 것을 나중에 만듦
        - 메세지 관련된 공통 전략을 편리하게 도입 가능
        - 중요한 메세지는 필요할 때 구체적으로 적어서 사용하는 방식이 효과적

    - typeMismatch
        - 전달받은 데이터의 type이 맞지 않는 경우 스프링이 자동으로 만들어주는 오류 코드
        - errors.properties에 typeMismatch로 오류 메세지 세팅해주는 것으로 핸들링 가능
        - 소스코드를 건들지 않고 원하는 메시지를 단계별 설정 가능
    
    - Validator
        - 스프링이 제공하는 인터페이스인 Validator를 사용하면 추가적인 도움을 받을 수 있음
        - WebDataBinder 파라미터로 받는 @InitBinder 메서드를 생성하여 WebDataBinder에 validator를 추가 -> 컨트롤러가 호출될 때 항상 해당 validator가 호출됨
        - 검증기를 등록하고 @Validated 어노테이션을 붙여서 등록한 검증기를 통해 검증
            - 여러 검증기가 등록된 경우 어떤 검증기가 실행 되어야 할지 구분 필요 -> supports() 메서드로
        - WebMvcConfigurer
            - 인터페이스의 getValidator() 메서드를 구현하여 글로벌하게 적용 가능
    
##### BeanValidation
    - 특정 필드에 대한 검증 로직은 대체로 일반적인 로직
    - 검증 로직을 모든 프로젝트에 적용할 수 있게 공통화 및 표준화 한것 -> Bean Validation
        - 애노테이션 기반을 검증로직 편리하게 적용 가능
    - Bean Validation
        - Bean Validation은 인터페이스로 구현체는 선택하여 적용
        - 자주 사용하는 구현체는 하이버네이트 Validator
    - 스프링 부트가 Bean Validator를 자롱을 사용하는 방법
        - spring-boot-starter-validation 라이브러리를 넣으면 자동으로 Bean Validator를 인식 및 통합
        - LocalValidatorFactoryBean을 글로벌 Validator로 등록
            - @NotNull 같은 애노테이션을 보고 검증 수행
            - @Valid, @Validated 적용해야 해당 객체를 검증
        - 검증 순서
            1. @ModelAttribute 각각의 필드에 타입 변환 시도
                - 성공시 다음으로
                - 실패 시 typeMismatch로 FieldError 추가
            2. Validator 적용
            - 즉, 타입 변환에 성공해야만 BeanValidation 적용이 의미있기 때문에 타입변환 성공한 필드만 BeanValidation 적용