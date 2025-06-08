package cn.valuetodays.api2.web;

import cn.vt.exception.CommonException;
import cn.vt.util.RSAUtils;
import jakarta.annotation.Priority;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Provider
@Priority(Priorities.ENTITY_CODER)
@Slf4j
public class DecryptRequestFilter implements ContainerRequestFilter {
    public static final String HEADER_ENCRYPT_REQUEST = "encryptrequest";

    public static final String PRIVATE_KEY_VALUE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC3FgbdH4teXVew/RM9HUOc9S901KKvcpVUNuclEPoM9hQr6aHoXDJxqlvAHyN4q0AOFpFGDvbdku4duIa0igIQUruMWx7hs4FUq+wsIytA5NSTKB7Iq8wIMjYx1p3QrndGxzKX+5e44yJkJnQxempaZGwf5wujXkgqiVztvHbElKWVU7NZFB+Af+rJ9fSHvxTJn4tgtOeqStwu+OJ0TU4HoaFDOkPvX1EYfINC3BoUqWuUoKlOrwEACyZFV7LBZgRrWrMmxoBJeHXeGMEYYLWgHSsMbrCieMK1l75qzxi/gj3a9T+pCEwBBSd51Cf1FocP2sNN50vS8HD5nFjevIWHAgMBAAECggEAKDkAnQfXGI4tO8b9uQl61zrOiD5G14C26d99/Ji0NrDBUbxsQ54tvucMbTLqGhKVagLvO5MCDT/AbGsjvPos5eUnKvjB2hgTq8RxiGjZkdI0GXhQ+wkzNAHEAkMCPNx3e+c7aERv2sCbx6DjGhOo1d8GGiQqXGPUOvQepGQpO5qFSQHtAZjw8wB7P0ajxqJ50g3SX3ilq5/v2a5WXnqBwtjgacGDwpXec8wnYSvYOTB/mXQUfrTlRUjPnHp/w61mcrcjhqrPVk67GgivYHQEyFpBB6tvN94ldOlP1ESdHK6Ons62mZepDJpPGWTbEgw+1qth4k5amWwxLczI3aSrsQKBgQDKrmaEFXq6LxDcFrSkJTxYnPWVDhESvQeoku4PdpZ853ZqxCQ4tBboCuGRLUeiQVke2kQcJbfSBrQtZ1QbsegCknbrui2Qgs//Mrzdn9RkxXra2bJG8/LCH8fTWItBA+XRIq+WpZhzwJZdud/0UnfCjpZ/7yM/kB1mfc54mHKPuwKBgQDnP/voC3xVSAU5lyoTBTIKSpy54e49U9yMO6uFa0vGikUdM6DGIsCxbLgDYPJJlBMD1G7dsMgsRKfJA/SCE6HQfLlsEp+WG66XKHbszZWBCCspOal6ldsTMzmOKMRVc6R5SLHz08f839OJSK9QEUvuczdhY4bVCiB7I6+aLV6GpQKBgGrJAig7LOMlYwuiRuBo4eeKIvW6aJJn174gU58ACv+Z79EGsFLTx3HQMdIMej5pkavosZPm53eXopx9YVoKHMQmKNO/zrvhv/aj49AcoonJZhYiNoSdiNxwZBbdAtlpVU8fqEWFz9HEuTR+dxlBzelKuM6EzxxxMJxMUllFcK57AoGBANQstdHDcBHDYd/kXZtUAgTFTqWSsYedgVc9jHN+7ei9hwpzC0mgK1LfYLrg4Y03CndSG2LWx+MuHxBly5rprmdfeLW7JKeSwI86dltxiYH5X7iUinbKNlaYgm934iRRVZBebcA9CVpwd2OXyBrbs+KV3jNCIT0o7IJvTLhWdkttAoGAXE7UgNIJ12tA1zwOi4DRcbFFHHKk+pWxYXh7O4lDscqsqNJdYgY0iDnbtMXrhptMe+OkhmnMvHXYZjrKzm/JZeTMt9CXpKrQgSHKA5t26VyfsYpV/+NzDpKo62PTItgEAngn2Ogg3u8xpL+ZzS6Bsy4Xvp9x77p5yvZgtxAhwLo=";
    public static final String PUBLIC_KEY_VALUE =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtxYG3R+LXl1XsP0TPR1DnPUvdNSir3KVVDbnJRD6DPYUK+mh6FwycapbwB8jeKtADhaRRg723ZLuHbiGtIoCEFK7jFse4bOBVKvsLCMrQOTUkygeyKvMCDI2Mdad0K53Rscyl/uXuOMiZCZ0MXpqWmRsH+cLo15IKolc7bx2xJSllVOzWRQfgH/qyfX0h78UyZ+LYLTnqkrcLvjidE1OB6GhQzpD719RGHyDQtwaFKlrlKCpTq8BAAsmRVeywWYEa1qzJsaASXh13hjBGGC1oB0rDG6wonjCtZe+as8Yv4I92vU/qQhMAQUnedQn9RaHD9rDTedL0vBw+ZxY3ryFhwIDAQAB";


    @Override
    public void filter(ContainerRequestContext requestContext) {
        /*
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        for (Map.Entry<String, List<String>> e : headers.entrySet()) {
            log.info("header name={}, value={}", e.getKey(), e.getValue());
        }
         */
        String encryptrequestFlag = requestContext.getHeaderString(HEADER_ENCRYPT_REQUEST);
        if (!"true".equals(encryptrequestFlag)) {
            return;
        }
//        log.info("begin to decrypt request payload");
        // 读取原始 body 数据
        try (InputStream originalStream = requestContext.getEntityStream();) {
            byte[] bytes = originalStream.readAllBytes();

            // 解析 JSON
            try (JsonReader reader = Json.createReader(new ByteArrayInputStream(bytes))) {
                JsonObject json = reader.readObject();
                String encrypted = json.getString("data");
//                log.info("encrypted={}", encrypted);
                // 解密
                String decryptedJson = RSAUtils.decrypt(encrypted, PRIVATE_KEY_VALUE);
//                log.info("decryptedJson={}", decryptedJson);
                // 替换请求体为解密后的 JSON
                requestContext.setEntityStream(new ByteArrayInputStream(decryptedJson.getBytes()));
            }
        } catch (Exception e) {
            throw new CommonException("请求解密失败: " + e.getMessage(), e);
        }
    }
}
