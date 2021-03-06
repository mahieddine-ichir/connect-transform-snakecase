package net.thinatech.connect.transform;

import com.google.common.base.CaseFormat;
import org.apache.kafka.connect.errors.ConnectException;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SnakeTransformer {

    public Object convert(Object value) {
        // TODO manage other formats?
        if (value instanceof Map) {
            return convert((Map) value);
        } else {
            throw new ConnectException("Unhandled value class type "+value.getClass());
        }
    }

    private Map convert(Map<?, ?> input) {
        return input.entrySet().stream()
                .filter(simpleEntry -> Objects.nonNull(simpleEntry.getKey()))
                .filter(simpleEntry -> Objects.nonNull(simpleEntry.getValue()))
                .map(e -> {
                    String key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getKey().toString());
                    if (e.getValue() instanceof Map) {
                        return new AbstractMap.SimpleEntry<String, Object>(key, convert((Map) e.getValue()));
                    } else if (e.getValue() instanceof List) {
                        Object collect = ((List) e.getValue()).stream().map(v -> {
                            if (v instanceof Map) {
                                return convert((Map) v);
                            } else {
                                return v;
                            }
                        }).collect(Collectors.toList());
                        return new AbstractMap.SimpleEntry(key, collect);
                    } else {
                        return new AbstractMap.SimpleEntry<String, Object>(key, e.getValue());
                    }
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }
}
