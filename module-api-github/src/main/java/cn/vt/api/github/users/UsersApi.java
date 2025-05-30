package cn.vt.api.github.users;

import cn.vt.api.github.BaseApiGithub;
import cn.vt.api.github.vo.Repository;
import cn.vt.api.github.vo.UserRepoVo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-25
 */
public class UsersApi extends BaseApiGithub {

    public UsersApi() {
    }

    public UsersApi(String apiKey) {
        super(apiKey);
    }

    public List<UserRepoVo> publicRepos(String username) {
        return getList("/users/{username}/repos", UserRepoVo[].class, username);
    }

    /**
     * NOTE: 传TOKEN
     */
    public List<Repository> authenticatedRepos() {
        // https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28
        List<Repository> list = new ArrayList<>();
        int page = 1;
        while (true) {
            String path = "/user/repos?type=all&sort=created&direction=desc&per_page=30&page={page}&since=&before=";
            List<Repository> paged = getList(path, Repository[].class, page);
            if (CollectionUtils.isEmpty(paged)) {
                break;
            }
            list.addAll(paged);
            page++;
        }
        return list;
    }

}
