package cn.valuetodays.api2.web.common;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-16
 */
public interface IVtNatsClient {
    void publish(String topic, String msg);

    void publishApplicationMessage(String msg);

    void publishApplicationException(String msgPrefix, Throwable ex);
}
