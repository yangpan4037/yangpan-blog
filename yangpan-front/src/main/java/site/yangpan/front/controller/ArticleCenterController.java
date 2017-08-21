package site.yangpan.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.yangpan.core.domain.Blog;
import site.yangpan.core.domain.Catalog;
import site.yangpan.core.domain.User;
import site.yangpan.core.service.BlogService;
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
@RequestMapping("/article/{username}")
public class ArticleCenterController {

    //用户详情service
    @Autowired
    private UserDetailsService userDetailsService;

    //分类service
    @Autowired
    private CatalogService catalogService;

    //文章service
    @Autowired
    private BlogService blogService;

    /**
     * 分类管理
     *
     * @param username
     * @return
     */
    @GetMapping("/category")
    public String category(@PathVariable("username") String username, Model model) {

        //通过用户名查询拥有的分列列表
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> categoryList = catalogService.listCatalogs(user);

        // 判断操作用户是否是分类的所有者
        boolean isOwner = false;
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }

        model.addAttribute("isCatalogsOwner", isOwner);
        model.addAttribute("user", user);
        model.addAttribute("categoryList", categoryList);


        return "article/category";
    }

    /**
     * 添加分类
     *
     * @param catalogVO
     * @return
     */
    @PostMapping("/addCategory")
    @PreAuthorize("authentication.name.equals(#catalogVO.username)")// 指定用户才能操作方法
    public ResponseEntity<Response> create(@RequestBody CatalogVO catalogVO) {

        String username = catalogVO.getUsername();
        Catalog catalog = catalogVO.getCatalog();

        User user = (User) userDetailsService.loadUserByUsername(username);

        try {
            catalog.setUser(user);
            catalogService.saveCatalog(catalog);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }

    /**
     * 添加文章
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/addArticle")
    public ModelAndView addArticle(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> categoryList = catalogService.listCatalogs(user);
        model.addAttribute("user", user);
        model.addAttribute("blog", new Blog(null, null, null));
        model.addAttribute("categoryList", categoryList);
        return new ModelAndView("article/contribute", "model", model);
    }

    /**
     * 编辑博客
     *
     * @param username
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/editArticle/{id}")
    public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        // 获取用户分类列表
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> categoryList = catalogService.listCatalogs(user);
        model.addAttribute("user", user);
        model.addAttribute("blog", blogService.getBlogById(id));
        model.addAttribute("categoryList", categoryList);
        return new ModelAndView("article/contribute", "model", model);
    }


    /**
     * 保存文章
     *
     * @param username
     * @param blog
     * @return
     */
    @PostMapping("/saveArticle")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        // 对 Catalog 进行空处理
        if (blog.getCatalog().getId() == null) {
            return ResponseEntity.ok().body(new Response(false, "未选择分类"));
        }
        try {

            // 判断是修改还是新增

            if (blog.getId() != null) {
                Blog orignalBlog = blogService.getBlogById(blog.getId());
                orignalBlog.setTitle(blog.getTitle());
                orignalBlog.setContent(blog.getContent());
                orignalBlog.setSummary(blog.getSummary());
                orignalBlog.setCatalog(blog.getCatalog());
                orignalBlog.setTags(blog.getTags());
                blogService.saveBlog(orignalBlog);
            } else {
                User user = (User) userDetailsService.loadUserByUsername(username);
                blog.setUser(user);
                blogService.saveBlog(blog);
            }

        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
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


}
