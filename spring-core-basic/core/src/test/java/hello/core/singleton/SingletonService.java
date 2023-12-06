package hello.core.singleton;

public class SingletonService {


    //1. static 영역에 객체를 1개만 생성
    private static final SingletonService instance = new SingletonService();

    //2. public으로 객체인스턴스 필요 시 해당 메서드를 통해서만 조회
    public static SingletonService getInstance() {
        return instance;
    }

    //3. private으로 선언하여 외부에서 new를 통해 객체 생성 못하게 방지
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
