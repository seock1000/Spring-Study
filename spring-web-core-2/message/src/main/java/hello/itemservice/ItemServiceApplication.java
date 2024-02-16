package hello.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	/*
	// 따로 등록안해도 스프링 부트에서 자동 등록하여 제공하는 bean
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		 // 설정된 파일의 이름을 지정 -> messages로 지정하면 messages.properties 파일을 읽어서 사용
		 // 국제화 파일은 _en, _ko 등의 형식으로 언어정보를 추가, 국제화 파일이 없으면 messages.properties를 기본으로 사용
		 // /resources 하위에 위치
		 // 여러 파일 한번에 지정 가능
		messageSource.setBasenames("messages", "errors");
		// 인코딩 정보 지정
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;
	}
	*/

}
