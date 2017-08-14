package site.yangpan.core.service;

import site.yangpan.core.domain.Authority;

/**
 * 权限业务层接口
 * Created by yangpn on 2017-08-06 22:47
 */
public interface AuthorityService {


    /**
     * 根据id获取 Authority
     *
     * @param id
     * @return
     */
    Authority getAuthorityById(Long id);
}
