package site.yangpan.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.yangpan.core.domain.Catalog;
import site.yangpan.core.domain.User;
import site.yangpan.core.service.CatalogService;
import site.yangpan.core.util.ConstraintViolationExceptionHandler;
import site.yangpan.core.vo.CatalogVO;
import site.yangpan.core.vo.Response;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 文章中心controller
 * Created by yangpn on 2017-08-20 20:17
 */
@Controller
@RequestMapping("/{username}/article")
public class ArticleCenterController {

    //用户详情service
    @Autowired
    private UserDetailsService userDetailsService;

    //分类service
    @Autowired
    private CatalogService catalogService;

    /**
     * 文章中心默认执行
     *
     * @param username
     * @return
     */
    @GetMapping("/")
    public String defaultExecute(@PathVariable("username") String username) {
        return "redirect:/" + username + "/article/articleManager";
    }

    /**
     * 文章管理
     *
     * @param username
     * @return
     */
    @GetMapping("/articleManager")
    public String articleManager(@PathVariable("username") String username) {
        return "article/articleManager";
    }

    /**
     * 分类管理
     *
     * @param username
     * @return
     */
    @GetMapping("/category")
    public String category(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> categoryList = catalogService.listCatalogs(user);
        model.addAttribute("categoryList", categoryList);
        return "article/category";
    }

    /**
     * 添加分类
     * @param catalogVO
     * @return
     */
    @PostMapping
    @PreAuthorize("authentication.name.equals(#catalogVO.username)")// 指定用户才能操作方法
    public ResponseEntity<Response> create(@RequestBody CatalogVO catalogVO) {

        String username = catalogVO.getUsername();
        Catalog catalog = catalogVO.getCatalog();

        User user = (User)userDetailsService.loadUserByUsername(username);

        try {
            catalog.setUser(user);
            catalogService.saveCatalog(catalog);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }
}
