package hello.servlet.web.springmvc.old;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller 인터페이스를 구현하여 사용하는 예전 방식
 * 스프링 빈 이름으로 url 매핑
 */
@Component("/springmvc/old-controller") // 빈 이름을 urlPattern으로 지정하여 핸들러 매핑 등록
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form"); // 컨트롤러는 호출되나 뷰가 없어서 오류 -> application.properties 파일에 prefix, suffix 설정 시 자동으로 ViewResolver가 view name 변환
    }
}
