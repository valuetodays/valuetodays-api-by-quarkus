package cn.valuetodays.api2.web.controller;

import cn.vt.R;
import cn.vt.web.req.SimpleTypesReq;
import io.quarkus.redis.datasource.RedisDataSource;
import io.vertx.mutiny.redis.client.Response;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-01
 */
@Path("/tool")
public class ToolController {

    @Inject
    RedisDataSource redisDataSource;

    @Path("executeRedisCmd")
    @POST
    public R<String> executeRedisCmd(SimpleTypesReq req) {
        String cmd = req.getText();
        if (StringUtils.isBlank(cmd)) {
            return R.fail("illegal cmd");
        }
        String strip = cmd.strip();
        String cmdStr = StringUtils.substringBefore(strip, " ");
        String argsStr = StringUtils.substring(strip, cmdStr.length() + 1);
        Response execute = redisDataSource.execute(cmdStr, argsStr);
        if (Objects.isNull(execute)) {
            return R.success("result is null");
        }
        return R.success(execute.toString());
    }

}
