package site.yangpan.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.yangpan.core.domain.User;
import site.yangpan.core.domain.es.EsArticle;
import site.yangpan.core.vo.TagVO;

import java.util.List;

/**
 * 博文全文检索业务层接口
 * Created by yangpn on 2017-08-06 22:53
 */
public interface EsArticleService {

    /**
     * 删除Article
     *
     * @param id
     * @return
     */
    void removeEsArticle(String id);

    /**
     * 更新 EsArticle
     *
     * @param esArticle
     * @return
     */
    EsArticle updateEsArticle(EsArticle esArticle);

    /**
     * 根据id获取Article
     *
     * @param ArticleId
     * @return
     */
    EsArticle getEsArticleByArticleId(Long ArticleId);

    /**
     * 最新博客列表，分页
     *
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsArticle> listNewestEsArticles(String keyword, Pageable pageable);

    /**
     * 最热博客列表，分页
     *
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsArticle> listHotestEsArticles(String keyword, Pageable pageable);

    /**
     * 博客列表，分页
     *
     * @param pageable
     * @return
     */
    Page<EsArticle> listEsArticles(Pageable pageable);

    /**
     * 最新前5
     *
     * @return
     */
    List<EsArticle> listTop5NewestEsArticles();

    /**
     * 最热前5
     *
     * @return
     */
    List<EsArticle> listTop5HotestEsArticles();

    /**
     * 最热前 30 标签
     *
     * @return
     */
    List<TagVO> listTop30Tags();

    /**
     * 最热前12用户
     *
     * @return
     */
    List<User> listTop12Users();
}
