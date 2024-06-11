
# How to

- Start all the services:

```
docker-compose up
```

- Run the application:
```
mvn spring-boot:run
```

- Attach to the Kafka log:

```
docker exec -it ddd-petstore-level7-big-ball-of-mud-kafka-1 bash

cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --from-beginning --topic dbserver1.all_tables.original
```

- Make an order and look at the Kafka log
```
http localhost:8085/customers id="park@naver.com" address[zipcode]="123" address[detail]="용인"

http PUT :8085/pet-order userId:"park@naver.com" orderItems[0][productId]='TV' orderItems[0][qty]=5 orderItems[0][price]=5000 orderItems[1][productId]='Phone' orderItems[1][qty]=2 orderItems[1][price]=1000 

```