package cn.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.service.users.dao.impl.ServiceUserDaoImpl;
import cn.users.Users;

/**
 * 控制器
 * 
 * @author Administrator
 * 
 */
@RestController
public class LoginController {
	@Autowired
	private ServiceUserDaoImpl service;
	@Autowired
	private HttpServletRequest request;
	/**
	 * 访问localhost:9090/admin 需要授权所以跳转到这里
	 * @return
	 */
	// 登录
	@GetMapping("/doLogin")
	public String doLogin(String username, String password) {
		// 添加用户认证信息
		// 用户登录
		String string = UUID.randomUUID().toString();
		service.Login(username, password);
		if(username==null||password==null){
			return "账号密码不能为空";
		}else{
			// 按照name查询权限 传进方法调用数据库
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username,password);
			try {
				// 进行验证 把当前登录成功的用户名传入session
				request.getSession().setAttribute("username", username);
				subject.login(token);
			} catch (Exception e) {
				return "login failed";
			}
		}
		
		return "登录成功";
	}
	/**
	 * 拿到登录之后的当前登录用户人session发送给ajax调用
	 * @param request
	 * @return
	 */
	@GetMapping("usernameGet")
	public String showUsername() {
		String username = (String) request.getSession().getAttribute("username");
		return username;
	}
	/**
	 * 按照用户名拿到权限
	 * @return
	 */
	@RequestMapping("findByusername")
	public String FindUserPermission() {
		//获取到session中登录成功的用户名
		String permission = service.findPermission((String)request.getSession().getAttribute("username"));
		UsernamePasswordToken token = new UsernamePasswordToken();
		return permission;

	}
	/**
	 * 按照id删除一个用户
	 * @param id
	 */
	@RequestMapping("deleteByID")
	public void deleteid(Integer id){
			service.deleteById(id);
	}
	/**
	 * 添加一个用户
	 * @return
	 */
	@RequestMapping("addAusername")
	public void adduser(String username,String password,String role,String permission){
		Users s = new Users();
		s.setPassword(password);
		s.setUsername(username);
		s.setPermission(permission);
		s.setRole(role);
		service.addoneman(s);
	}
	/**
	 * 修改一个用户
	 * @param username
	 * @param permission
	 */
	@RequestMapping("updatePermission")
	public void updatePer(String username,String permission){
		service.updateByName(username, permission);
	}
	/**
	 * 查找到用户名
	 * @return
	 */
	@RequestMapping("findUsername")
	public void findusername(String username){
		service.findUsername(username);
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}

	// 登出
	@RequestMapping(value = "/logout")
	public String logout() {
		return "退出登录";
	}

	// 错误页面展示
	@GetMapping("/error")
	public String error() {
		return "错误页面";
	}

	@RequiresRoles("admin")
	// 判断是否是admin角色
	@RequiresPermissions("create")
	// 判断是否具有create权限
	@RequestMapping(value = "/create")
	public String create() {
		return "Create success!";
	}

	@RequiresPermissions("detail")
	// 判断是否有权限detail
	@RequestMapping(value = "/detail")
	public String detail() {
		return "uid";
	}
}
