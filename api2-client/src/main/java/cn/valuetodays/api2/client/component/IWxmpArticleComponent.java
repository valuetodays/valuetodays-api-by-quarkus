package cn.valuetodays.api2.client.component;

import io.smallrye.mutiny.tuples.Tuple2;

import java.io.File;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-05
 */
public interface IWxmpArticleComponent {
    Tuple2<String, List<File>> downloadImages(File file, String url);
}
