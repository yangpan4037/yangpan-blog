package site.yangpan.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import site.yangpan.core.domain.User;

/**
 * 个人中心controller
 * Created by yangpn on 2017-08-20 20:17
 */
@Controller
@RequestMapping("/member/{username}")
public class MemberController {

    //用户详情service
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/center")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView center(@PathVariable("username") String username, Model model) {
        User user = (User)userDetailsService.loadUserByUsername(username);
        model.addAttribute("user",user);
        return new ModelAndView("member/center", "model", model);
    }


    @GetMapping("/aboutMe")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView aboutMe(@PathVariable("username") String username, Model model) {
        User user = (User)userDetailsService.loadUserByUsername(username);
        model.addAttribute("user",user);
        return new ModelAndView("member/aboutMe", "model", model);
    }

    @GetMapping("/changeEmail")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView changeEmail(@PathVariable("username") String username, Model model) {
        User user = (User)userDetailsService.loadUserByUsername(username);
        model.addAttribute("user",user);
        return new ModelAndView("member/changeEmail", "model", model);
    }


    @GetMapping("/changeFace")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView changeFace(@PathVariable("username") String username, Model model) {
        User user = (User)userDetailsService.loadUserByUsername(username);
        model.addAttribute("user",user);
        return new ModelAndView("member/changeFace", "model", model);
    }

    @GetMapping("/changePhone")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView changePhone(@PathVariable("username") String username, Model model) {
        User user = (User)userDetailsService.loadUserByUsername(username);
        model.addAttribute("user",user);
        return new ModelAndView("member/changePhone", "model", model);
    }

    @GetMapping("/changePwd")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView changePwd(@PathVariable("username") String username, Model model) {
        User user = (User)userDetailsService.loadUserByUsername(username);
        model.addAttribute("user",user);
        return new ModelAndView("member/changePwd", "model", model);
    }

    @GetMapping("/otherAccount")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView otherAccount(@PathVariable("username") String username, Model model) {
        User user = (User)userDetailsService.loadUserByUsername(username);
        model.addAttribute("user",user);
        return new ModelAndView("member/otherAccount", "model", model);
    }


}
