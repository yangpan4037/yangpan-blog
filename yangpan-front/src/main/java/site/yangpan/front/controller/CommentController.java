package site.yangpan.front.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.yangpan.core.domain.Blog;
import site.yangpan.core.domain.Comment;
import site.yangpan.core.domain.User;
import site.yangpan.core.service.BlogService;
import site.yangpan.core.service.CommentService;
import site.yangpan.core.util.ConstraintViolationExceptionHandler;
import site.yangpan.core.vo.Response;

/**
 * 评论controller
 * Created by yangpn on 2017-08-06 23:24
 */
@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    /**
     * 获取评论列表
     *
     * @param articleId
     * @param model
     * @return
     */
    @GetMapping("/article/{articleId}")
    public String listComments(@PathVariable(value = "articleId", required = true) Long articleId, Model model) {
        Blog blog = blogService.getBlogById(articleId);
        List<Comment> commentList = blog.getComments();

        // 判断操作用户是否是评论的所有者
        String commentOwner = "";
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                commentOwner = principal.getUsername();
            }
        }
        model.addAttribute("commentOwner", commentOwner);
        model.addAttribute("commentList", commentList);
        return "/article/article :: #articleCommentsWrap";
    }

    /**
     * 发表评论
     *
     * @param articleId
     * @param commentContent
     * @return
     */
    @PostMapping("/article/{articleId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")//指定角色权限才能操作方法
    public ResponseEntity<Response> createComment(@PathVariable("articleId") Long articleId, String commentContent) {
        try {
            blogService.createComment(articleId, commentContent);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }

    /**
     * 删除评论
     *
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Long articleId) {

        boolean isOwner = false;
        User user = commentService.getCommentById(id).getUser();

        // 判断操作用户是否是评论的所有者
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }

        if (!isOwner) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }

        try {
            blogService.removeComment(articleId, id);
            commentService.removeComment(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "删除评论成功！", null));
    }
}
