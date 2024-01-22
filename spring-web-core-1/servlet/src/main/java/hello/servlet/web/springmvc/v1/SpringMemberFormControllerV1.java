package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// @Controller : 스프링 빈으로 등록(내부에 @Component), 스프링 MVC에서 애노테이션 기반 컨트롤러로 인식
@Controller
public class SpringMemberFormControllerV1 {

    // @RequestMapping : 요청정보를 매핑, 해당하는 url이 호출되면 해당 메서드 호출, 애노테이션 기반으로 메서드 이름은 상관 X
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        // 모델과 뷰 정보를 담아 반환
        return new ModelAndView("new-form");
    }
}
