package site.yangpan.core.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import site.yangpan.core.domain.es.EsBlog;

/**
 * 日志全文检索持久层接口
 * Created by yangpn on 2017-08-06 22:43
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {

    /**
     * 模糊查询(去重)
     * @param title
     * @param Summary
     * @param content
     * @param tags
     * @param pageable
     * @return
     */
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String Summary, String content, String tags, Pageable pageable);

    EsBlog findByBlogId(Long blogId);
}
