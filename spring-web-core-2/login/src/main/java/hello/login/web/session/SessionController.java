package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "세션이 없습니다.";
        }

        // 세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        // 세션 ID
        log.info("sessionId={}", session.getId());
        // 세션의 유효시간 : 초단위
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        // 생성 일시
        log.info("creationTime={}", new Date(session.getCreationTime()));
        // 세션과 연결된 사용자가 최근에 서버에 접근한 시간
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        // 새로 생성된 세션인지 여부
        log.info("isNew={}", session.isNew());

        return "세션 출력";

    }
}
