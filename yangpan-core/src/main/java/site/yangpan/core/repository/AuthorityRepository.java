package site.yangpan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yangpan.core.domain.Authority;

/**
 * 权限持久层接口
 * Created by yangpn on 2017-08-06 22:42
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
