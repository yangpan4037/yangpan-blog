package site.yangpan.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.yangpan.core.domain.Article;
import site.yangpan.core.domain.Catalog;
import site.yangpan.core.domain.User;

/**
 * Created by yangpn on 2017-08-06 22:49
 */
public interface ArticleService {
    /**
     * 保存Article
     *
     * @param article
     * @return
     */
    Article saveArticle(Article article);

    /**
     * 删除Article
     *
     * @param id
     * @return
     */
    void removeArticle(Long id);

    /**
     * 根据id获取Article
     *
     * @param id
     * @return
     */
    Article getArticleById(Long id);

    /**
     * 根据用户名进行分页模糊查询（最新）
     *
     * @param user
     * @return
     */
    Page<Article> listArticlesByTitleVote(User user, String title, Pageable pageable);

    /**
     * 根据用户名进行分页模糊查询（最热）
     *
     * @param suser
     * @return
     */
    Page<Article> listArticlesByTitleVoteAndSort(User suser, String title, Pageable pageable);

    /**
     * 根据分类进行查询
     *
     * @param catalog
     * @param pageable
     * @return
     */
    Page<Article> listArticlesByCatalog(Catalog catalog, Pageable pageable);

    /**
     * 阅读量递增
     *
     * @param id
     */
    void readingIncrease(Long id);

    /**
     * 发表评论
     *
     * @param ArticleId
     * @param commentContent
     * @return
     */
    Article createComment(Long ArticleId, String commentContent);

    /**
     * 删除评论
     *
     * @param ArticleId
     * @param commentId
     * @return
     */
    void removeComment(Long ArticleId, Long commentId);

    /**
     * 点赞
     *
     * @param ArticleId
     * @return
     */
    Article createVote(Long ArticleId);

    /**
     * 取消点赞
     *
     * @param ArticleId
     * @param voteId
     * @return
     */
    void removeVote(Long ArticleId, Long voteId);

    /**
     * 查询下一篇
     *
     * @param id
     * @return
     */
    Article findNextByCurrentId(Long id);

    /**
     * 查询上一篇
     *
     * @param id
     * @return
     */
    Article findPrevByCurrentId(Long id);
}
