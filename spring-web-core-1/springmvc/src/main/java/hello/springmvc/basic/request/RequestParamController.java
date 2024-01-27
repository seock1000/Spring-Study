package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age")int memberAge
    ) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("request-param-v4")
    public String requestParamV4(String username, int age) { // String, int 등 단순 타입이면 @RequestParam도 생략 가능
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam.required
     * default : true
     * /request-param-required -> username이 없으므로 예외 : 400 Bad Request
     *
     * 주의!
     * /request-param-required?username= -> 빈문자로 통과
     *
     * 주의!
     * /request-param-required
     * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는
    defaultValue 사용)
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * 파라미터 Map으로 조회
     * MulitValueMap으로도 조회 가능 -> 하나의 키에 여러 값이 있을 때
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        /*
        // @ModelAttribute가 해주는 역할
        // 값이 잘못 들어오면(ex) int age = abc) BindException
        @RequestParam String username,
        @RequestParam int age
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);
         */

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        //log.info("helloDate={}", helloData.toString());
        return "ok";
    }

    /**
     * @ModelAttribute 생략 가능 : ArgumentResolver(HttpServletRequest 등) 제외
     * 생략 시,
     * 단순 타입(String, Integer) 등에는 @RequestParam 자동 적용
     * 나머지는 @ModelAttribute 자동 적용
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
