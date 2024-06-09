# 시작하기



## Base Project 다운로드 및 실행
먼저, 새로운 브라우저 탭을 열고, base project 를 gitpod 로 접속합니다
https://gitpod.io/#https://github.com/msa-school/ddd-petstore-level6-layered-spring-jpa

GidPod 내에 터미널을 열고(왼쪽 상단의 햄버거 버튼 > Terminal > New Terminal), 프로젝트가 잘 컴파일 되는지 확인합니다:
```
mvn spring-boot:run
```

## 미션: Store 업무 영역의 추가
- 진열대에 Item 들이 진열됨
- 고객이 Item 의 세부 사항을 검색함
- 검색한 Item 을 Cart에 하나 이상 담음
- Cart Item 들을 구매함
- 구매방식은 신용카드와 현금 입금이 있음
- 구매상태는 지불대기, 지불완료, 실패가 있음

## 사용예

> http 도구설치: pip install httpie


```
# 회원등록
http localhost:8080/customers id="park@naver.com" address[zipcode]="123" address[detail]="용인"

# 카트에 뽀삐담기
http :8080/cartItems customer="http://localhost:8083/customers/park@naver.com" items[]="http://localhost:8080/pets/1"

# 카트에 담긴 뽀삐확인
http "http://localhost:8080/cartItems/2/items"
```

## 문제
- Pet 전문가와 쇼핑몰 프로세스 전문가 영역의 충돌
- Core Domain과 Supporting Domain 의 간섭, 장애전파, 복잡성의 범람
- Ubiquitous Language 의 손상 (Store Domain: Item 을 Item 이라 부르지 못하고 Pet 을 사용)

## 추가 변경사항
- Cat 과 Dog 를 따로 등록할 수 있도록 CatRepository.java 와 DogRepository.java 를 만들었음
- 따라서 다음과 같이 Cat 과 Dog 를 http 로 등록할 수 있음:
```
http :8080/cats name="몽이" price[currency]="KR_WON" price[amount]=100000
http :8080/dogs name="춘삼이" price[currency]="EURO" price[amount]=200000

http :8080/cartItems customer="http://localhost:8083/customers/park@naver.com" items:='["http://localhost:8080/cats/4", "http://localhost:8080/dogs/5"]'

```

## 다음: 도메인 영역의 분리와 연동
- Pet <-> Store 도메인의 분리 (Bounded Context)
- 도메인간 연동 (Context Mapping, Anti-corruption)
- https://github.com/msa-school/ddd-petstore-level9-bounded-multi-model




# Debezium

## 
```
docker run -it --rm --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 quay.io/debezium/zookeeper:2.5

docker run -it --rm --name kafka -p 9092:9092 --link zookeeper:zookeeper quay.io/debezium/kafka:2.5

docker run -it --rm --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=debezium -e MYSQL_USER=mysqluser -e MYSQL_PASSWORD=mysqlpw quay.io/debezium/example-mysql:2.5

docker run -it --rm --name mysqlterm --link mysql mysql:8.2 sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'

docker run -it --rm --name connect -p 8083:8083 -e GROUP_ID=1 -e CONFIG_STORAGE_TOPIC=my_connect_configs -e OFFSET_STORAGE_TOPIC=my_connect_offsets -e STATUS_STORAGE_TOPIC=my_connect_statuses --link kafka:kafka --link mysql:mysql quay.io/debezium/connect:2.5

curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{ "name": "petstore-connector", "config": { "connector.class": "io.debezium.connector.mysql.MySqlConnector", "tasks.max": "1", "database.hostname": "mysql", "database.port": "3306", "database.user": "debezium", "database.password": "dbz", "database.server.id": "184054", "topic.prefix": "dbserver1", "database.include.list": "petstore", "schema.history.internal.kafka.bootstrap.servers": "kafka:9092", "schema.history.internal.kafka.topic": "schemahistory.petstore" } }'

docker run -it --rm --name watcher --link zookeeper:zookeeper --link kafka:kafka quay.io/debezium/kafka:2.5 watch-topic -a -k dbserver1.petstore.pet
```