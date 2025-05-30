package cn.vt.api.github.users;

import cn.vt.api.github.vo.Repository;
import cn.vt.api.github.vo.UserRepoVo;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests for {@link UsersApi}.
 *
 * @author lei.liu
 * @since 2024-09-25
 */
public class UsersApiTest {
    private UsersApi usersApi = new UsersApi();

    @Test
    void publicRepos() {
        String username = "valuetodays";
        List<UserRepoVo> publicRepos = usersApi.publicRepos(username);
        MatcherAssert.assertThat(publicRepos, CoreMatchers.notNullValue());
    }

    @Test
    void authenticatedRepos() {
        List<Repository> publicRepos = usersApi.authenticatedRepos();
        MatcherAssert.assertThat(publicRepos, CoreMatchers.notNullValue());
        StringBuilder sb = new StringBuilder();
        for (Repository r : publicRepos) {
            sb.append("git cl " + r.getSshUrl()).append("\n");
        }
        System.out.println(sb.toString());
    }
}
