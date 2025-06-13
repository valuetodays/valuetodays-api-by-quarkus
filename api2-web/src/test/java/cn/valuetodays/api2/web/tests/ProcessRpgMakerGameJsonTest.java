package cn.valuetodays.api2.web.tests;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.vt.test.jupiter.condition.EnabledOnFile;
import cn.vt.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-11-19
 */
public class ProcessRpgMakerGameJsonTest {
    public static final String JSON_FILE = "x:/Enemies.json";

    @Test
    @EnabledOnFile(path = JSON_FILE)
    public void process() throws IOException {
        File file = new File(JSON_FILE);
        String fileAsString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        List<Map<String, Object>> list = JsonUtils.fromJson(fileAsString, new TypeReference<List<Map<String, Object>>>() {
        });
        for (Map<String, Object> map : list) {
            System.out.println(map);
            if (Objects.nonNull(map)) {
                Object params = map.get("params");
                if (Objects.isNull(params)) {
                    continue;
                }
                Object exp = map.get("exp");
                map.put("exp", ((Integer) exp) * 10);
                Object gold = map.get("gold");
                map.put("gold", ((Integer) gold) * 100);
                //noinspection unchecked
                List<Integer> paramsAsIntList = (List<Integer>) params;
                paramsAsIntList.set(0, 1); // 将所有敌人的hp置为1
            }
        }
        File file2 = new File("x:/Enemies2.json");
        FileUtils.write(file2, JsonUtils.toJson(list), StandardCharsets.UTF_8);
    }
}
