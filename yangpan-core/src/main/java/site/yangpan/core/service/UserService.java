package site.yangpan.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.yangpan.core.domain.User;

import java.util.Collection;
import java.util.List;

/**
 * 用户业务层实现类
 * Created by yangpn on 2017-08-06 23:02
 */
public interface UserService {
    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    User saveUser(User user);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    void removeUser(Long id);

    /**
     * 删除列表里面的用户
     *
     * @param users
     * @return
     */
    void removeUsersInBatch(List<User> users);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 获取用户列表
     *
     * @return
     */
    List<User> listUsers();

    /**
     * 根据用户名进行分页模糊查询
     *
     * @param name
     * @param pageable
     * @return
     */
    Page<User> listUsersByNameLike(String name, Pageable pageable);

    /**
     * 更具名称列表查询
     *
     * @param usernames
     * @return
     */
    List<User> listUsersByUsernames(Collection<String> usernames);

    /**
     * 根据名称查询
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    User findByEmail(String email);
}
