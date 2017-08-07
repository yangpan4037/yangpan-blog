package site.yangpan.core.service;

import site.yangpan.core.domain.Comment;

/**
 * 评论业务层接口
 * Created by yangpn on 2017-08-06 23:01
 */
public interface CommentService {
    /**
     * 根据id获取 Comment
     *
     * @param id
     * @return
     */
    Comment getCommentById(Long id);

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    void removeComment(Long id);
}
