package cn.valuetodays.api2.basic.service;

import cn.valuetodays.api2.basic.RedisKeyConstant;
import cn.valuetodays.api2.basic.vo.CreateAuthcodeResp;
import cn.vt.util.Randoms;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.SetArgs;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;
import java.util.UUID;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-04-23
 */
@ApplicationScoped
@Slf4j
public class AuthcodeServiceImpl  {

    private static final Random random = Randoms.RANDOM;
    private static final String AUTHCODE_KEY = RedisKeyConstant.AUTHCODE_KEY;

    @Inject
    RedisDataSource stringRedisTemplate;

    private String getAuthcode() {
        int i = random.nextInt(9000) + 1000;
        return String.valueOf(i);
    }

    public CreateAuthcodeResp create() {
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.key(String.class).del(AUTHCODE_KEY + uuid);
        String authcode = getAuthcode();
        stringRedisTemplate.value(String.class).set(AUTHCODE_KEY + uuid, authcode, new SetArgs().ex(Duration.ofMinutes(5))); // 5min
        BufferedImage image = getImage(authcode);

        byte[] encode = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(image, "PNG", out);
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray())) {
                encode = IOUtils.toByteArray(inputStream);
            }
        } catch (IOException e) {
            log.error("error when create authcode", e);
        }

        return CreateAuthcodeResp.of(encode, uuid);
    }

    private static final Font font = new Font("Times New Roman", Font.PLAIN, 20);

    private BufferedImage getImage(String authcode) {
        int width = 60;
        int height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        g.setFont(font);

        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        for (int i = 0; i < 4; i++) {
            String rand = authcode.substring(i, i + 1);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                .nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }
        g.dispose();

        return image;
    }

    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
