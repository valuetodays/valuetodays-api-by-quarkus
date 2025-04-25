package valuetodays.demo.commons;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-24
 */
@Data
public class SimpleTypeReq implements Serializable {
    private Long id;
    private String text;
}
