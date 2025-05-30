package cn.vt.api.github.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-25
 */
@Data
public class LicenseSimple implements Serializable {
    private String key; //  "apache-2.0",
    private String name; // Apache License 2.0",
    private String spdx_id; //  "Apache-2.0",
    private String url; // "https://api.github.com/licenses/apache-2.0",
    private String node_id; // "MDc6TGljZW5zZTI="
}
