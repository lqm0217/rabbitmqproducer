## swagger-ui
http://localhost:8082/swagger-ui.html#

## RabbitMQ

### docker run
```
docker run --hostname local-rabbit --name docker-rabbitmq  -v /Users/admin/Documents/docker/rabbitmq/data:/var/lib/rabbitmq -p 5672:5672 -p 15672:15672 -d rabbitmq:3-management
```
> I used macbook, if use windows PC, change [/Users/admin/Documents/docker/rabbitmq/data] to local data file save path

> use [rabbitmq:3-management], don't use [rabbitmq] latest version has problem.

### docker exec
```
docker exec -it docker-rabbitmq bash
```

### confirm the log
```
docker logs docker-rabbitmq
```

### access
http://localhost:15672
>user:guest

>password:guest

### Set
| Exchange   | Type  | Queue    | Routing key |
|----------  |:------|:--------:|------------:|
| amq.fanout | fanout| fanout   |             |
| amq.direct | direct| direct1  |  direct1    |
| amq.direct | direct| direct2  |  direct2    |
| amq.topic  | topic | topic1   |   topic.#   |
| amq.topic  | topic | topic2   |   topics.#  |
