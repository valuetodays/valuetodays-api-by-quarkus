package cn.vt.api.github.meta;

import cn.vt.api.github.BaseApiGithub;
import cn.vt.api.github.vo.RootVo;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-25
 */
public class MetaApi extends BaseApiGithub {

    public MetaApi() {
        super();
    }

    public MetaApi(String apiKey) {
        super(apiKey);
    }

    public RootVo root() {
        return get("/", RootVo.class);
    }


}
