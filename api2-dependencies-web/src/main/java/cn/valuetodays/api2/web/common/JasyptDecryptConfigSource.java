package cn.valuetodays.api2.web.common;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.jasypt.util.text.BasicTextEncryptor;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JasyptDecryptConfigSource implements ConfigSource {

    private final Map<String, String> properties = new HashMap<>();
    private final String encryptionPassword;

    public JasyptDecryptConfigSource() {
        this.encryptionPassword = getEncryptPassword();

        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword(encryptionPassword);

        String profile = System.getProperty("quarkus.profile", System.getenv().getOrDefault("QUARKUS_PROFILE", null));

        List<String> yamlFiles = new ArrayList<>();
        yamlFiles.add("application.yml");
        if (profile != null && !profile.isEmpty()) {
            yamlFiles.add("application-" + profile + ".yml");
        }

        for (String file : yamlFiles) {
            try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file)) {
                if (in != null) {
                    Yaml yaml = new Yaml();
                    Map<String, Object> loaded = yaml.load(in);
                    flattenAndDecrypt("", loaded, encryptor);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to load or parse: " + file, e);
            }
        }
    }

    public static String getEncryptPassword() {
        // 从系统变量或环境变量读取解密密钥
        return System.getProperty(
            "jasypt.encryptor.password",
            System.getenv("JASYPT_ENCRYPTOR_PASSWORD")
        );
    }

    @SuppressWarnings("unchecked")
    private void flattenAndDecrypt(String prefix, Map<String, Object> map, BasicTextEncryptor encryptor) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                flattenAndDecrypt(key, (Map<String, Object>) value, encryptor);
            } else if (value instanceof List) {
                // Flatten list to key.0, key.1, ...
                List<?> list = (List<?>) value;
                for (int i = 0; i < list.size(); i++) {
                    String listKey = key + "." + i;
                    Object item = list.get(i);
                    properties.put(listKey, decryptIfNeeded(item.toString(), encryptor));
                }
            } else {
                properties.put(key, decryptIfNeeded(value.toString(), encryptor));
            }
        }
    }

    private String decryptIfNeeded(String value, BasicTextEncryptor encryptor) {
        if (value.startsWith("ENC(") && value.endsWith(")")) {
            String enc = value.substring(4, value.length() - 1);
            return encryptor.decrypt(enc);
        }
        return value;
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return properties.get(propertyName);
    }

    @Override
    public String getName() {
        return "JasyptDecryptConfigSource";
    }

    @Override
    public int getOrdinal() {
        return 270; // 比 application.properties 默认值略高（250），用于覆盖
    }
}
