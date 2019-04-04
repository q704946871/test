package cn.service.users.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.users.Users;

public interface ServiceUserDao {
	/**
	 * 用户进行登录
	 * @param username
	 * @param password
	 * @return
	 */
	public Users Login(@Param("username")String username,@Param("password")String password);
	/**
	 * 查找用户名的权限
	 * @param name
	 * @return
	 */
	public String findPermission(String username);
	/**
	 * 按照id删除一个用户
	 */
	public Integer deleteById(Integer id);
	/**
	 * 增加一个用户
	 */
	public void addoneman(Users u);
	/**
	 * 按照名字修改权限
	 */
	public void updateByName(@Param("username")String username,@Param("permission")String permission);
	/**
	 * 查找到用户名
	 */
	public List<Users> findUsername(String username);
	/**
	 * 查找所有人
	 */
	List<Users> findall();
}
