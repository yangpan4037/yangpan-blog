package com.yangpan.background.query;

import org.apache.commons.lang3.StringUtils;

import com.yangpan.core.domain.User;

/**
 *	员工查询条件,继承公共的查询条件类
 */
public class UserQuery extends BaseQuery {
	
	private String username;
	
	private String email;
	
	private String ages = "18,60";
	
	private Long deptId;
	
	/**
	 * 构造器
	 * 调用父类的构造器传入查询员工需要的类名，用于拼接hql
	 */
	public UserQuery() {
		super(User.class.getName());
	}
	
	/**
	 * 覆写父类的回调得到where方法，里面调用父类拼接查询条件hql的where语句
	 */
	@Override
	protected void callBackGetWhere() {
		if(StringUtils.isNotBlank(username)){
			setWhereHql("o.username like ?", "%" + username + "%");
		}
		if(StringUtils.isNotBlank(email)){
			setWhereHql("o.email like ?", "%" + email + "%");
		}
		if(deptId != null && deptId != -1){
			setWhereHql("o.department.id = ?", deptId);
		}
		if (StringUtils.isNotBlank(ages)) {
			String[] ageArr = ages.split(",");
		    if (ageArr != null && ageArr.length == 2) {
		    	setWhereHql("o.age between ? and ?", Integer.parseInt(ageArr[0]), Integer.parseInt(ageArr[1]));
		    }
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAges() {
		return ages;
	}

	public void setAges(String ages) {
		this.ages = ages;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
}
