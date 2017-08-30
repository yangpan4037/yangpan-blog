package site.yangpan.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.yangpan.core.domain.Article;
import site.yangpan.core.domain.Catalog;
import site.yangpan.core.domain.User;

/**
 * 文章持久层接口
 * Created by yangpn on 2017-08-06 22:41
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {
    /**
     * 根据用户名分页查询用户列表（最新）
     * 由 findByUserAndTitleLikeOrTagsLikeOrderByCreateTimeDesc 代替，可以根据标签进行查询
     *
     * @param user
     * @param title
     * @param pageable
     * @return
     * @see findByTitleLikeOrTagsLikeAndUserOrderByCreateTimeDesc
     */
    @Deprecated
    Page<Article> findByUserAndTitleLikeOrderByCreateTimeDesc(User user, String title, Pageable pageable);

    /**
     * 根据用户名分页查询用户列表
     *
     * @param user
     * @param title
     * @param sort
     * @param pageable
     * @return
     */
    Page<Article> findByUserAndTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据用户名分页查询用户列表
     *
     * @param user
     * @param title
     * @param sort
     * @param pageable
     * @return
     */
    Page<Article> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title, User user, String tags, User user2, Pageable pageable);

    /**
     * 根据用户名分页查询用户列表
     *
     * @param user
     * @param title
     * @param sort
     * @param pageable
     * @return
     */
    Page<Article> findByCatalog(Catalog catalog, Pageable pageable);

    /**
     * 查询下一篇
     *
     * @param id
     * @return
     */
    @Query(value = "select * from article where id > ?1 order by id asc limit 1", nativeQuery = true)
    Article findNextByCurrentId(Long id);

    /**
     * 查询上一篇
     *
     * @param id
     * @return
     */
    @Query(value = "select * from article where id < ?1 order by id desc limit 1", nativeQuery = true)
    Article findPrevByCurrentId(Long id);
}

