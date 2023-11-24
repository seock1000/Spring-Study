package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // 정적 컨텐츠 반환
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "spring!!");
        // controller에서 문자를 return 하면 viewResolver가 화면을 찾아 처리
        // templates/{viewName}+.html 로 해석
        return "hello";
    }

    // template engine 사용하여 반환
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    // api 방식
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // html 양식 없이 문자 그대로 전달
    }
}
