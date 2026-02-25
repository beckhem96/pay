# 프로젝트 아키텍처 가이드: 헥사고날 아키텍처 (Ports and Adapters)

본 프로젝트는 비즈니스 로직을 외부 기술(Web, DB 등)로부터 격리하고 유연성을 극대화하기 위해 **헥사고날 아키텍처** 구조를 채택하고 있습니다.

## 1. 계층 구조 및 구성 요소

### ① Application Core (안쪽 원)
비즈니스 로직의 핵심이며 외부 라이브러리나 프레임워크에 의존하지 않습니다.
- **Domain (`domain/`)**: 비즈니스 규칙의 정수. 외부 시스템과 무관한 순수 도메인 모델 (`Membership`).
- **Service (`application/service/`)**: 비즈니스 로직의 구현체. 포트 인터페이스를 구현하고 도메인 엔티티를 조작하여 유스케이스를 실행합니다.

### ② Ports (인터페이스 계층)
애플리케이션 코어와 외부 세계 사이의 명세(Contract)입니다.
- **Inbound Port (`application/port/in/`)**: 외부에서 내부로 들어오는 요청의 통로.
    - `UseCase`: 외부(Web)가 애플리케이션 기능을 호출하기 위한 인터페이스.
    - `Command`: 입력 데이터의 유효성을 검증하고 캡슐화하는 **입력 모델 (Input Model)**.
- **Outbound Port (`application/port/out/`)**: 내부에서 외부로 나가는 요청의 통로.
    - `Port`: 서비스가 DB 저장이나 외부 API 호출을 위해 사용하는 인터페이스.

### ③ Adapters (바깥쪽 원)
포트를 통해 코어와 통신하며, 구체적인 기술 구현을 담당합니다.
- **Inbound Adapter (`adapter/in/web/`)**: HTTP 요청을 처리하는 Web 어댑터 (Controller).
- **Outbound Adapter (`adapter/out/persistence/`)**: 실제 DB 접근 및 영속성을 담당하는 Persistence 어댑터.

---

## 2. 데이터 흐름 (Data Flow)

1. **Request**: 외부에서 `Web Adapter`로 요청이 들어옵니다.
2. **Command Creation**: 어댑터는 요청 데이터를 기반으로 `Command` 객체를 생성합니다 (이 과정에서 입력 값 유효성 검증 수행).
3. **Use Case Call**: `Web Adapter`는 `Inbound Port(UseCase)` 인터페이스를 통해 `Service`를 호출합니다.
4. **Business Logic**: `Service`는 비즈니스 로직을 수행하며 `Domain` 모델을 사용합니다.
5. **Persistence**: 저장이 필요한 경우 `Outbound Port` 인터페이스를 호출합니다.
6. **Implementation**: `Persistence Adapter`가 해당 인터페이스의 실제 구현을 실행하여 DB와 통신합니다.

---

## 3. 이 아키텍처의 장점

- **기술 독립성**: DB나 프레임워크를 변경해도 핵심 비즈니스 로직(Core)은 수정할 필요가 없습니다.
- **테스트 용이성**: 외부 의존성이 포트로 격리되어 있어 모킹(Mocking)을 통한 유닛 테스트가 매우 쉽습니다.
- **유지보수성**: 도메인 로직이 응용 프로그램 로직과 명확히 분리되어 코드가 직관적입니다.
