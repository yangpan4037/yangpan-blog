package site.yangpan.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.yangpan.core.domain.User;
import site.yangpan.core.repository.UserRepository;
import site.yangpan.core.service.UserService;

import java.util.Collection;
import java.util.List;

/**
 * 用户业务层实现类
 * Created by yangpn on 2017-08-06 23:09
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userRepository.delete(id);
    }

    @Transactional
    @Override
    public void removeUsersInBatch(List<User> users) {
        userRepository.deleteInBatch(users);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        // 模糊查询
        name = "%" + name + "%";
        Page<User> users = userRepository.findByRealnameLike(name, pageable);
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new AuthenticationServiceException("找不到该用户");
        }
        return user;
    }

    @Override
    public List<User> listUsersByUsernames(Collection<String> usernames) {
        return userRepository.findByUsernameIn(usernames);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
