# STOMP와 Redis Pub/Sub을 이용한 간단한 채팅 서버 프로토 타입

## 기술 스펙
- JAVA11
- Spring Boot 2.5.4
- STOMP
- MySQL
- Redis
- JPA

## Local 환경
#### docker
docker 설치 필요
```
brew install docker
```

#### mysql, redis
local 디렉토리 밑에서 다음 커맨드를 실행하면 mysql, redis 이미지를 다운로드 후 실행
```
docker-compose up -d
```

#### mysql 초기 설정
아래 디렉토리에 있는 sql들을 실행
```
local/mysql/
```

매번 sql들을 실행하기 귀찮다면 flyway를 설정하는 것을 권장

## 웹소켓 서버 주의사항
1. Redis에 적용한 Serializer인 `GenericJackson2JsonRedisSerializer`는 Package & Class 정보까지 넣기에 다른 프로젝트에서 Redis에 publish or save를 할 경우 해당 data를 
   deserialize할 때 ClassNotFoundException이 발생할 것이다. 
만약 이 문제가 발생한다면 해결방법들은 아래와 같다.
- Redis에 연결되는 서버는 웹소켓 서버로만 제한한다.
- Redis의 Serializer/Deserializer를 교체한다. `JdkSerializationRedisSerializer` or `StringRedisSerializer`
2. 클라이언트에서 STOMP, SockJs에 대한 지식이 있어야함. 웹소켓 통신 메세지 템플릿이 STOMP로 강제된다.
3. 클라이언트에서 `messagePublishedAt`을 보고 정렬을 해야한다.
4. 클라이언트에서 주기적으로 message 조회 api를 통해 불러가서 유실된 메세지가 없는지 봐야한다.
5. 클라이언트에서 웹소켓과의 연결 상태를 지속적으로 확인해서 연결이 유실되었을 경우 어떻게 대응할 것인지 전략을 세워야한다.
6. 온라인인 유저들에게 실시간 메세지를 전달할 수 있는 Websocket 서버와 `이전 메세지 이력 조회`, `채팅방 만들기, 입장하기, 퇴장하기` 등 어플리케이션의 기능을 담당하는 API 서버를 분리한다.

## Redis pub/sub에 대하여..
### Redis 7버전 이하는 Redis Cluster 환경에서 pub/sub을 지원하지 않는다.
7 버전 이후에 지원하는 pub/sub도 Sharding을 하는 구조라 Node들이 부하를 받을 수 있는 구조. 효율적이진 않고 고려사항이 존재한다.
고가용성(High Availability)을 고려한다면 Redis Pub/Sub은 다소 부족한 것 같다. Nats.io나 kafka가 대안이 될 수도..  
[참고](https://github.com/redis/redis/issues/1927)

### Redis pub/sub은 메세지의 순서 보장을 하지 않고, 최소 1회 메세지 전달(At Least Once)을 보증하지 않는다.
Redis pub/sub은 아래 이유들로 인하여 특정 조건을 만족하는 시스템이나 내결함성(Fault Tolerance) 시스템을 고려한다면 그 기준을 준수하지 못한다. 
- 메세지의 전달 순서를 보장하지 않는다. (들어온 순서대로 나가는게 아니다.)
- 메세지를 전달할 당시 subscriber들이 아예 존재하지 않거나, 모든 subscriber들이 어떠한 이유로 메세지를 전달받지 못하는 상황일 때 메세지는 유실된다.
- redis가 어떠한 이유로 failover 되면, 당시에 publish된 메세지들은 유실된다.

보다 안전한 Message Streaming or Queueing이 필요하다면 MQ나 Kafka를 고려한다.




