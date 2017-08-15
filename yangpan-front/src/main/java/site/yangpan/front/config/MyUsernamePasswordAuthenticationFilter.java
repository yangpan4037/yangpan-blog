package site.yangpan.front.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.yangpan.core.util.ImagesCaptchaUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yangpn on 2017-08-14 16:44
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //是否开启验证码功能
    private boolean isOpenValidateCode = true;

    //验证码参数名
    public static final String VALIDATE_CODE = "validateCode";

    /**
     * 覆写尝试认证方法
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (isOpenValidateCode) {
            checkValidateCode(request);
        }
        return super.attemptAuthentication(request, response);
    }

    /**
     * 校验图片验证码
     *
     * @param request
     */
    protected void checkValidateCode(HttpServletRequest request) {
        //获取session
        HttpSession session = request.getSession();

        //通过ImagesCaptchaUtil获取session里的验证码
        String sessionImagesCaptchaCode = ImagesCaptchaUtil.getSessionImagesCaptchaCode(session);

        //获取request的验证码
        Object obj = request.getParameter(VALIDATE_CODE);
        String requestImagesCaptchaCode = null == obj ? "" : obj.toString();

        //判断验证码正确性
        if (StringUtils.isEmpty(requestImagesCaptchaCode) || !sessionImagesCaptchaCode.equalsIgnoreCase(requestImagesCaptchaCode)) {
            throw new AuthenticationServiceException("验证码错误！");
        }

        //通过ImagesCaptchaUtil移除session里的验证码
        ImagesCaptchaUtil.removeSessionImagesCaptchaCode(session);
    }
}
