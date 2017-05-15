package com.yangpan.background.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangpan.background.service.IRoleService;
import com.yangpan.core.domain.Role;

/**
 *	【角色】业务层实现类
 *	继承公共的业务层实现类，实现【角色】业务层接口，具有【角色】的CRUD方法
 */
@Service//配置注解service层（service层一般配置@Service）
@Transactional//配置给这个类的方法添加事务
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService{

}
