package cn.vt.api.github.meta;

import cn.vt.api.github.vo.RootVo;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MetaApi}.
 *
 * @author lei.liu
 * @since 2024-09-25
 */
public class MetaApiTest {
    private MetaApi metaApi = new MetaApi();

    @Test
    void testRoot() {
        RootVo root = metaApi.root();
        MatcherAssert.assertThat(root, CoreMatchers.notNullValue());
    }

}
