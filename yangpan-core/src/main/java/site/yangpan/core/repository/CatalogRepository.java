package site.yangpan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yangpan.core.domain.Catalog;
import site.yangpan.core.domain.User;

import java.util.List;

/**
 * 分类持久层接口
 * Created by yangpn on 2017-08-06 22:40
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    /**
     * 根据用户查询
     * @param user
     * @return
     */
    List<Catalog> findByUser(User user);

    /**
     * 根据用户查询
     * @param user
     * @param name
     * @return
     */
    List<Catalog> findByUserAndName(User user, String name);
}
