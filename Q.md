# FAQ: 스프링 의존성 주입과 헥사고날 아키텍처

## Q. 어댑터에서 Port를 `implements` 하지 않으면 왜 주입(Autowired) 에러가 발생하나요?

### 1. 스프링의 타입 기반 주입 원리
스프링은 의존성을 주입할 때 해당 필드나 생성자 파라미터의 **타입(Type)**을 확인합니다.
- **상황**: `FindMembershipService`는 `FindMembershipPort`라는 **인터페이스 타입**의 객체를 필요로 합니다.
- **문제**: 어댑터 클래스(`MembershipPersistenceAdapter`)가 해당 인터페이스를 `implements` 하지 않으면, 스프링 입장에서 해당 어댑터는 인터페이스와 아무런 관련이 없는 별개의 클래스일 뿐입니다.
- **결과**: "타입이 일치하는 빈(Bean)을 찾을 수 없다"는 에러와 함께 애플리케이션 실행이 실패합니다.

### 2. 의존성 역전 원칙 (DIP) 준수
헥사고날 아키텍처의 핵심은 **고수준 모듈(Service)이 저수준 모듈(Adapter)에 의존하지 않게 하는 것**입니다.
- 서비스는 인터페이스(Port)만 알고 있습니다.
- 실제 구현체(Adapter)가 이 인터페이스를 구현함으로써, 서비스는 구체적인 구현 기술(JPA, MyBatis 등)이 무엇인지 몰라도 기능을 사용할 수 있게 됩니다.

---

## Q. 프로젝트 코드 어디에서 주입이 일어나고 있나요?

본 프로젝트는 **생성자 주입(Constructor Injection)** 방식을 사용하며, `Lombok`을 통해 이를 간결하게 처리하고 있습니다.

### 1. 빈(Bean) 등록 (주입 대상 알리기)
클래스 상단의 커스텀 어노테이션을 통해 스프링 컨테이너에 관리 대상임을 알립니다.
- `@UseCase`, `@PersistenceAdapter`, `@WebAdapter` 등 (이 어노테이션들은 내부에 `@Component`를 포함하고 있습니다.)

### 2. 생성자를 통한 주입 (Lombok 활용)
```java
@UseCase
@RequiredArgsConstructor // (1) final 필드를 인자로 받는 생성자 자동 생성
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort; // (2) 주입받을 대상
    
    // Lombok이 아래와 같은 생성자를 자동으로 만들어줍니다.
    // public RegisterMembershipService(RegisterMembershipPort port) {
    //     this.registerMembershipPort = port;
    // }
}
```

### 3. 주입 시점
1. 스프링이 구동되면서 `@Component`가 붙은 클래스들의 객체를 생성합니다.
2. `RegisterMembershipService`를 생성하려고 보니 `RegisterMembershipPort` 타입의 객체가 필요함을 확인합니다.
3. 해당 인터페이스를 구현한 `MembershipPersistenceAdapter` 객체를 찾아 생성자의 인자로 전달합니다. **(이때 `implements`가 되어 있어야만 타입 매칭이 성공합니다.)**
