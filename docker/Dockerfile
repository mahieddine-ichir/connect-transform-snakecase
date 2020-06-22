FROM confluentinc/cp-kafka-connect:5.4.1

ADD ../target/connect-transform-snakecase-1.0-SNAPSHOT.jar /usr/share/java/kafka-connect-elasticsearch/

ADD delete-unused.sh /tmp/
RUN chmod +x /tmp/delete-unused.sh
RUN /tmp/delete-unused.sh
