package site.yangpan.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.yangpan.core.domain.User;

import java.util.Collection;
import java.util.List;

/**
 * 用户持久层接口
 * Created by yangpn on 2017-08-06 22:38
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名分页查询用户列表
     * @param name
     * @param pageable
     * @return
     */
    Page<User> findByNameLike(String name, Pageable pageable);
    /**
     * 根据名称查询
     * @param username
     * @return
     */
    User findByUsername(String username);
    /**
     * 根据名称列表查询
     * @param usernames
     * @return
     */
    List<User> findByUsernameIn(Collection<String> usernames);
}
