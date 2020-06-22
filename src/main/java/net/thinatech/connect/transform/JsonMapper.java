package net.thinatech.connect.transform;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.transforms.Transformation;

import java.util.Map;

public class JsonMapper implements Transformation<SinkRecord> {

    private final SnakeTransformer convertValue = new SnakeTransformer();

    @Override
    public SinkRecord apply(SinkRecord record) {
        return new SinkRecord(record.topic(),
                record.kafkaPartition(),
                record.keySchema(),
                record.key(),
                record.valueSchema(),
                convertValue.convert((byte [])record.value()),
                record.kafkaOffset(),
                record.timestamp(),
                record.timestampType(),
                record.headers()
                );
    }

    @Override
    public ConfigDef config() {
        return new ConfigDef().define("none", ConfigDef.Type.STRING, ConfigDef.Importance.LOW, "no doc");
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }
}
