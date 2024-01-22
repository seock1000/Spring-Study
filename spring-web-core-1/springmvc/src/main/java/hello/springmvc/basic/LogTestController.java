package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// RestController : 반환 값으로 뷰를 찾지 않고 HTTP 메세지 바디에 바로 입력
@RestController
@Slf4j
public class LogTestController {

    // private final Logger log = LoggerFactory.getLogger(getClass()); // 롬복이 제공하는 @Slf4j 어노테이션으로 대체 가능

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        //System.out.println("name = " + name);
        // 로그는 성능이 System.out 보다 좋으며, 파일로 남기기 용이 -> System.out 안쓰는게 맞음
        // 로그 레벨 지정
        log.trace("trace log={}", name);
        //log.trace("trace log=" + name); // 출력 결과는 똑같지만 자바 언어 특성 상 출력되는 레벨이 아니어도 연산이 발생 -> 의미없는 연산으로 리소스 낭비, 따라서 이렇게 하면 안됨
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
