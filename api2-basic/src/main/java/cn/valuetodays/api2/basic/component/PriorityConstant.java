package cn.valuetodays.api2.basic.component;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-30
 */
public class PriorityConstant {
    // 设置优先级较高的@StartupEvent方法，值越高，越先运行
    public static final int NATS_CLIENT_INIT_ORDER = 1;
    public static final int NATS_CONSUMER_ORDER = 11111;
}
