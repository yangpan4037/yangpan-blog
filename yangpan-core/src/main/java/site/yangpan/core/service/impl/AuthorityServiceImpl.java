package site.yangpan.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yangpan.core.domain.Authority;
import site.yangpan.core.repository.AuthorityRepository;
import site.yangpan.core.service.AuthorityService;

/**
 * 权限业务持久层实现类
 * Created by yangpn on 2017-08-06 22:49
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findOne(id);
    }

}
