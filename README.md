# saga-pattern-example

## 기능 요구사항

- 사용자가 상품을 주문할 수 있다.
- 주문이 성공하면 수량이 감소한다.

## 개발 환경

- Java 17
- Spring Boot 3.1
- Gradle 8.1
- JPA
- Kafka

## 외부 라이브러리 목록

| Dependency                                            | Purpose                                       |
|-------------------------------------------------------|-----------------------------------------------|
| org.slf4j:slf4j-api                                   | SLF4J(Simple Logging Facade for Java)를 위한 API |
| ch.qos.logback:logback-classic                        | 로깅 프레임워크 Logback의 구현체                         |
| org.springframework.boot:spring-boot-starter-web      | Spring Boot 웹 애플리케이션 개발을 위한 스타터 의존성           |
| org.springframework.boot:spring-boot-starter-data-jpa | Spring Boot와 JPA를 사용하여 데이터베이스 액세스를 위한 스타터 의존성 |
| org.springframework.kafka:spring-kafka                | Spring과 Apache Kafka 통합을 위한 라이브러리             |
| com.h2database:h2                                     | H2 데이터베이스를 위한 의존성                             |

## Challenge

### 트랜잭션 아웃박스 패턴을 이용한 주문 생성과 수량 감소
트랜잭션 아웃박스 패턴을 사용하여 주문 생성과 재고 수량 감소를 구현할 때, 다음과 같이 동작합니다.

주문(Order) 서비스에서 주문 생성 작업을 시작합니다. 주문 정보는 데이터베이스에 저장되며, 해당 작업은 트랜잭션 내에서 처리됩니다.

주문 생성 작업이 성공하면 주문 서비스는 주문 완료 이벤트를 메시지 브로커(Kafka 등)를 통해 발행합니다. 이 이벤트는 다른 마이크로서비스에게 주문이 생성되었음을 알립니다.

재고(Stock) 서비스는 주문 완료 이벤트를 구독하고, 해당 이벤트를 수신하여 재고를 감소시킵니다. 이때, 수량 감소 작업은 외부 시스템과의 통신이 필요하므로 트랜잭션 아웃박스 패턴을 사용합니다. 주문 생성 트랜잭션과는 별개로 처리됩니다.

이러한 방식으로 트랜잭션 아웃박스 패턴을 사용하면 주문 생성과 수량 감소를 각각의 마이크로서비스에서 독립적으로 처리할 수 있습니다. 데이터베이스 트랜잭션은 각 마이크로서비스 내에서 유지되며, 외부 시스템과의 통신은 트랜잭션 아웃박스 패턴을 활용하여 안전하게 처리됩니다.

## API 명세

### 주문 생성 API (order-app)
```
POST /orders
```

> Request

```json
{
  "productId": 1
}
```

| 필드명       | 타입   | 설명     | 필수 여부
|-----------|------|--------| --- |
| productId | Long | 상품 아이디 | O |

## 다운로드 및 실행

1. 도커 컨테이너 실행 (docker-compose up -d)
2. 각 서비스 실행