# Kafka Connect snake case transform
A Kafka Connect transform that read Json data from Kafka (in bytes) and writes Json data in Snake case format.

* _Compatible with Confluent platform 5.4.1_
* _Uses guava CaseFormat_ (provided on confluent platform 5.4.1)

## Usage example (Elasticsearch sink connect)

`````json
{
  "name" : "ElasticsearchSinkConnector",
  "config" : {
    "connector.class" : "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "tasks.max" : "1",
    "confluent.topic.bootstrap.servers": "localhost:9092",
    "connection.url": "http://localhost:9200",
    "batch.size": "2000",
    "max.in.flight.requests": "5",
    "max.buffered.records": "20000",
    "retry.backoff.ms": "1000",
    "connection.timeout.ms": "5000",
    "write.method": "insert",
    "type.name": "doc",
    "schema.ignore": "true",
    "value.converter.schemas.enable": "false",
    "key.converter": "org.apache.kafka.connect.converters.IntegerConverter",
    "value.converter": "org.apache.kafka.connect.converters.ByteArrayConverter",
    "topics": "input_topic",
    "transforms": "snake",
    "transforms.snake.type": "net.thinatech.connect.transform.JsonSnakeMapper",
    "transforms.snake.none": ""
  }
}
`````

## Docker usage

* _Docker image uses _confluentinc_ connect official image to build image._
* Build jar before building image.
* The `delete-unused.sh` removes the official image the unused connectors to optimize image start, modify accordingly.

### Build the docker image
Run from the root folder
````bash
    docker build -f docker/Dockerfile . -t snake_transform
````

An example of a `docker-compose` is also provided, running a zookeeper, a kafka (single node), and a connector example, 
along with an elasticsearch node.

## Install connector

````bash
    curl -vX POST http://localhost:8083/connectors -H "Content-Type: application/json" -d @example-config-es.json
````
