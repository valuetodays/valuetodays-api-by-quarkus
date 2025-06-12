package cn.valuetodays.api2.module.tests;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cn.vt.util.DateUtils;
import lombok.Data;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-08-27
 */
public class ListWthMultiValuesToMapTest {
    @Test
    void testConvert() {
        List<Vo> dataList = List.of(
            new Vo(LocalDate.of(2024, 7, 27), Integer.valueOf(100)),
            new Vo(LocalDate.of(2024, 8, 27), Integer.valueOf(100)),
            new Vo(LocalDate.of(2024, 8, 28), Integer.valueOf(101)),
            new Vo(LocalDate.of(2024, 8, 29), Integer.valueOf(102))
        );

        MultiValuedMap<LocalDate, Integer> ldAndNetCashMap = new ArrayListValuedHashMap<>(dataList.size());
        dataList.forEach(e -> {
            ldAndNetCashMap.put(e.getKey(), e.getValue());
        });

        SortedMap<LocalDate, Integer> map = new TreeMap<>();
        for (Map.Entry<LocalDate, Collection<Integer>> e : ldAndNetCashMap.asMap().entrySet()) {
            LocalDate key = e.getKey();
            Collection<Integer> values = e.getValue();
            map.put(key, values.stream().reduce(Integer::sum).orElse(0));
        }

        Map<String, Integer> finalMap = map.entrySet().stream()
            .collect(
                Collectors.toMap(
                    e -> DateUtils.formatDate(e.getKey().atStartOfDay(), "yyyy-MM"),
                    Map.Entry::getValue,
                    Integer::sum
                )
            );
        assertThat(finalMap.size(), equalTo(2));
        assertThat(finalMap, hasEntry("2024-07", 100));
        assertThat(finalMap, hasEntry("2024-08", 303));
    }

    @Data
    private static class Vo {
        private LocalDate key;
        private Integer value;

        public Vo(LocalDate key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }
}
