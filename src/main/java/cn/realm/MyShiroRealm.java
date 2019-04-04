package cn.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.service.users.dao.ServiceUserDao;
import cn.users.Users;
/**
 * 读取数据表获得用户权限
 * @author Administrator
 *
 */
public class MyShiroRealm extends AuthorizingRealm{
	@Autowired
	private ServiceUserDao service;
	
    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken atoken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) atoken;
        String username = token.getUsername();
        String password = new String (token.getPassword());
        if (username == null) {
            return null;
        }
        //数据库代码
        Users u = service.Login(username, password);
        if(u==null){
        	return null;
        }else{
        	//这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
            return simpleAuthenticationInfo;
        }
        

    }
	//角色权限和对应权限添加
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        //获取登录用户名
        String username = (String) pc.getPrimaryPrincipal();
        List<Users> findUsername = service.findUsername(username);
        SimpleAuthorizationInfo info = null;
        for(Users s:findUsername){
        	 //添加角色和权限
            info = new SimpleAuthorizationInfo();
            //添加角色
            info.addRole(username);
            //添加权限
            info.addStringPermission(s.getPermission());
            System.out.println(s.getPermission());
        }
        System.out.println(info.getStringPermissions());
        return info;
    }

}
