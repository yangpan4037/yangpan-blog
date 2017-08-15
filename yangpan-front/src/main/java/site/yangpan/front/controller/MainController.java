package site.yangpan.front.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.yangpan.core.domain.Authority;
import site.yangpan.core.domain.User;
import site.yangpan.core.service.AuthorityService;
import site.yangpan.core.service.UserService;
import site.yangpan.core.util.ImagesCaptchaUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangpn on 2017-08-06 23:23
 */
@Controller
public class MainController {

    private static final Long ROLE_USER_AUTHORITY_ID = 2L;
    //
    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;
//
//    @GetMapping("/")
//    public String root() {
//        return "redirect:/index";
//    }
//
//    @GetMapping("/index")
//    public String index() {
//        return "redirect:/blogs";
//    }
//
//    /**
//     * 获取登录界面
//     *
//     * @return
//     */
//    @GetMapping("/login")
//    public String login() {
//        return "/login/login";
//    }
//
//    @GetMapping("/loginError")
//    public String loginError(Model model, HttpServletRequest request, HttpServletResponse response) {
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        System.out.println(parameterMap.size());
//        model.addAttribute("loginError", true);
//        model.addAttribute("errorMsg", "登录失败，账号或者密码错误！");
//        return "/login/login";
//    }
//
//    @GetMapping("/register")
//    public String register() {
//        return "/login/register";
//    }
//
//    /**
//     * 注册用户
//     *
//     * @param user
//     * @return
//     */
//    @PostMapping("/register")
//    public String registerUser(User user) {
//        List<Authority> authorities = new ArrayList<>();
//        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
//        user.setAuthorities(authorities);
//        userService.saveUser(user);
//        return "redirect:/login";
//    }
//
//    @GetMapping("/search")
//    public String search() {
//        return "search";
//    }

    /**
     * 图片验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/imageCaptcha")
    public void getCode3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImagesCaptchaUtil.createImagesCaptcha(request, response);
    }

    /**
     * 用户注册页面
     *
     * @return
     */
    @GetMapping("/register/page")
    public ModelAndView register() {
        return new ModelAndView("/login/register");
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> registerUser(User user, String captcha, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        //通过ImagesCaptchaUtil获取session里的验证码
        String sessionImagesCaptchaCode = ImagesCaptchaUtil.getSessionImagesCaptchaCode(request.getSession());

        //判断验证码正确性
        if (StringUtils.isEmpty(captcha) || !sessionImagesCaptchaCode.equalsIgnoreCase(captcha)) {
            result.put("success", false);
            result.put("errorField","captcha");
            result.put("message", "验证码错误！");
            return result;
        }

        //判断用户名和邮箱是否被注册

        User user1 = userService.findByUsername(user.getUsername());

        if(null != user1){
            result.put("success", false);
            result.put("errorField","username");
            result.put("message", "用户名已被注册！");
            return result;
        }

        User user2 = userService.findByEmail(user.getEmail());
        if(null != user2){
            result.put("success", false);
            result.put("errorField","email");
            result.put("message", "邮箱已被注册！");
            return result;
        }


        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        user.setAuthorities(authorities);
        System.out.println(user);
        userService.saveUser(user);


        result.put("success", true);
        result.put("redirectUrl", "/login/page");
        return result;
    }


    /**
     * 判断ajax请求
     *
     * @param request
     * @return
     */
    public boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(header) ? true : false;
        return isAjax;
    }


    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 登录页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login/page", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isAjaxRequest(request)) {
            response.sendRedirect("/login/ajax");
            return null;
        } else {
            return "/login/login";
        }
    }

    /**
     * 登录成功
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login/success", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loginSuccess(HttpServletRequest request, HttpServletResponse response) {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String redirectUrl = null;
        if (savedRequest != null) {
            redirectUrl = savedRequest.getRedirectUrl();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("redirectUrl", redirectUrl);
        return result;
    }

    /**
     * 登录失败
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login/failure", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loginFailure(HttpServletRequest request, HttpServletResponse response) {
        AuthenticationException ae = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", false);
        result.put("message", ae.getMessage());
        return result;
    }

    /**
     * ajax受限请求
     *
     * @return
     */
    @RequestMapping(value = "/login/ajax", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loginAjax() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", false);
        result.put("message", "你需要先登录，才能进行操作！");
        return result;
    }

}
