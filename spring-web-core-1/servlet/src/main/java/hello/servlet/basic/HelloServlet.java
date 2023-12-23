package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service"); // localhost:8080/hello 호출 시 빈화면 + 콘솔에 해당 메세지
        // request, response 출력
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // request에 파라미터 파싱
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        // response에 값 추가
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + username);
    }
}
