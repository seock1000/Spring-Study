package hello.servlet.web.frontcontroller.v1;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV1 {

    // 프론트 컨트롤러는 해당 인터페이스의 process 호출하여 구현과 관계없는 일관성 있는 로직을 가져갈 수 있음
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
