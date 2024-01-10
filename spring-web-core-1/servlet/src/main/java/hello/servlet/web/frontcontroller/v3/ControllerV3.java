package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    // 컨트롤러 구현에 servlet 종송성 제거
    ModelView process(Map<String, String> paramMap);
}
