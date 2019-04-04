package cn.service.users.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import cn.service.users.dao.ServiceUserDao;
import cn.users.Users;
import cn.users.dao.UsersDao;
@Component
public class ServiceUserDaoImpl implements ServiceUserDao{
	@Autowired
	private UsersDao dao;
	
	@Override
	public Users Login(String username, String password) {
		// TODO Auto-generated method stub
		return dao.Login(username, password);
	}
	@Override
	public String findPermission(String username) {
		// TODO Auto-generated method stub
		return dao.findPermission(username);
	}
	@Override
	public Integer deleteById(Integer id) {
		// TODO Auto-generated method stub
		return dao.deleteById(id);
	}
	@Override
	public void addoneman(Users u) {
		// TODO Auto-generated method stub
		dao.addoneman(u);
	}
	@Override
	public void updateByName(String username, String permission) {
		dao.updateByName(username, permission);
		
	}
	@Override
	public List<Users> findUsername(String username) {
		// TODO Auto-generated method stub
		return dao.findUsername(username);
	}
	//@Cacheable("finAll")
	public List<Users> findall() {
		System.out.println("数据库中读取记录");
		return dao.findall();
	}
	

}
