package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "spring!!");
        // controller에서 문자를 return 하면 viewResolver가 화면을 찾아 처리
        // templates/{viewName}+.html 로 해석
        return "hello";
    }
}
