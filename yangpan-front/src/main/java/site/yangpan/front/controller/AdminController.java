package site.yangpan.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import site.yangpan.core.vo.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangpn on 2017-08-06 23:29
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    /**
     * 获取后台管理主页面
     *
     * @return
     */
    @GetMapping
    public ModelAndView listUsers(Model model) {
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("用户管理", "/users"));
        model.addAttribute("list", list);
        return new ModelAndView("/admins/index", "model", model);
    }


}