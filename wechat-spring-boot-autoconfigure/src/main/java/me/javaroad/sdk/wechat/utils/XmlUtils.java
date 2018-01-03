package me.javaroad.sdk.wechat.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author heyx
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlUtils {

    private static final XmlMapper MAPPER = new XmlMapper();

    public static <T> T parse(String content, Class<T> clazz) throws IOException {
        return MAPPER.readValue(content, clazz);
    }

    public static <T> String xmlString(T t) throws JsonProcessingException {
        return MAPPER.writeValueAsString(t);
    }

    public static String readNode(String content, String nodeName) throws IOException {
        JsonNode jsonTree = MAPPER.readTree(content);
        JsonNode node = jsonTree.get(nodeName);
        if (Objects.nonNull(node)) {
            return node.asText();
        }
        return null;
    }
}
