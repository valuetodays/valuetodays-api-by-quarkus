package cn.valuetodays.api2.web.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import com.luciad.imageio.webp.WebPReadParam;
import org.apache.commons.lang3.StringUtils;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-16
 */
public class WebpToJpgUtils {
    public static String getImageType(File file) throws IOException {
        ImageInputStream image = ImageIO.createImageInputStream(new FileInputStream(file));
        Iterator<ImageReader> readers = ImageIO.getImageReaders(image);
        return readers.next().getFormatName();
    }

    public static File webpToJpg(File webpFile) throws IOException {
        String imageType = getImageType(webpFile);
        if (!StringUtils.contains(imageType, "WebP")) {
            webpFile.renameTo(new File(webpFile.getParentFile(), webpFile.getName() + ".jpg"));
            return webpFile;
        }

        /* Convert webp to jpg start */
        ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
        // configure decoding parameters
        WebPReadParam readParam = new WebPReadParam();
        readParam.setBypassFiltering(true);
        // configure the input on the ImageReader
        reader.setInput(new FileImageInputStream(webpFile));
        // Decode the image
        BufferedImage image = reader.read(0, readParam);
        // "jpg" can be "png" too
        File jpgFile = new File(
            webpFile.getParentFile(),
            webpFile.getName() + "_" + System.currentTimeMillis() + ".jpg"
        );
        ImageIO.write(image, "jpg", jpgFile);
        /* Convert webp to jpg end */
        return jpgFile;
    }
}
