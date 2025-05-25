package cn.valuetodays.api2.web.component;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * Tests for {@link WordPressComponent}.
 *
 * @author lei.liu
 * @since 2025-04-08
 */
public class WordPressComponentTest {

    private final WordPressComponent wordPressComponent = new WordPressComponent();

    @EnabledOnOs(OS.WINDOWS)
    @Test
    public void newPost() {
        String url = "https://valuetodays.github.io/statics/images/wp/mmpic/T6xLk7REm6NYqKkBBvnHFQ/aaa.png";
        wordPressComponent.newPost(url, null);
    }
}
