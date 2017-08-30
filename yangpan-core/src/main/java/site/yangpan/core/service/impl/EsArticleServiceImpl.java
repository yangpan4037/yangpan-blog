package site.yangpan.core.service.impl;


import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.SearchParseException;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import site.yangpan.core.domain.User;
import site.yangpan.core.domain.es.EsArticle;
import site.yangpan.core.repository.es.EsArticleRepository;
import site.yangpan.core.service.EsArticleService;
import site.yangpan.core.service.UserService;
import site.yangpan.core.vo.TagVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

/**
 * Created by yangpn on 2017-08-06 22:56
 */
@Service
public class EsArticleServiceImpl implements EsArticleService {
    @Autowired
    private EsArticleRepository esArticleRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private UserService userService;

    private static final Pageable TOP_5_PAGEABLE = new PageRequest(0, 5);
    private static final String EMPTY_KEYWORD = "";

    /* (non-Javadoc)
     * @see com.waylau.spring.boot.blog.service.EsArticleService#removeEsArticle(java.lang.String)
     */
    @Override
    public void removeEsArticle(String id) {
        esArticleRepository.delete(id);
    }

    /* (non-Javadoc)
     * @see com.waylau.spring.boot.blog.service.EsArticleService#updateEsArticle(com.waylau.spring.boot.blog.domain.es.EsArticle)
     */
    @Override
    public EsArticle updateEsArticle(EsArticle esArticle) {
        return esArticleRepository.save(esArticle);
    }

    /* (non-Javadoc)
     * @see com.waylau.spring.boot.blog.service.EsArticleService#getEsArticleByArticleId(java.lang.Long)
     */
    @Override
    public EsArticle getEsArticleByArticleId(Long ArticleId) {
        return esArticleRepository.findByArticleId(ArticleId);
    }

    /* (non-Javadoc)
     * @see com.waylau.spring.boot.blog.service.EsArticleService#listNewestEsArticles(java.lang.String, org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<EsArticle> listNewestEsArticles(String keyword, Pageable pageable) throws SearchParseException {
        Page<EsArticle> pages = null;
        Sort sort = new Sort(Direction.DESC, "createTime");
        if (pageable.getSort() == null) {
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }

        pages = esArticleRepository.findDistinctEsArticleByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);

        return pages;
    }

    /* (non-Javadoc)
     * @see com.waylau.spring.boot.blog.service.EsArticleService#listHotestEsArticles(java.lang.String, org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<EsArticle> listHotestEsArticles(String keyword, Pageable pageable) throws SearchParseException {

        Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize", "createTime");
        if (pageable.getSort() == null) {
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }

        return esArticleRepository.findDistinctEsArticleByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<EsArticle> listEsArticles(Pageable pageable) {
        return esArticleRepository.findAll(pageable);
    }


    /**
     * 最新前5
     *
     * @return
     */
    @Override
    public List<EsArticle> listTop5NewestEsArticles() {
        Page<EsArticle> page = this.listHotestEsArticles(EMPTY_KEYWORD, TOP_5_PAGEABLE);
        return page.getContent();
    }

    /**
     * 最热前5
     *
     * @return
     */
    @Override
    public List<EsArticle> listTop5HotestEsArticles() {
        Page<EsArticle> page = this.listHotestEsArticles(EMPTY_KEYWORD, TOP_5_PAGEABLE);
        return page.getContent();
    }

    @Override
    public List<TagVO> listTop30Tags() {

        List<TagVO> list = new ArrayList<>();
        // given
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withIndices("article").withTypes("article")
                .addAggregation(terms("tags").field("tags").order(Terms.Order.count(false)).size(30))
                .build();
        // when
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        StringTerms modelTerms = (StringTerms) aggregations.asMap().get("tags");

        Iterator<Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();

            list.add(new TagVO(actiontypeBucket.getKey().toString(),
                    actiontypeBucket.getDocCount()));
        }
        return list;
    }

    @Override
    public List<User> listTop12Users() {

        List<String> usernamelist = new ArrayList<>();
        // given
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withIndices("article").withTypes("article")
                .addAggregation(terms("users").field("username").order(Terms.Order.count(false)).size(12))
                .build();
        // when
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        StringTerms modelTerms = (StringTerms) aggregations.asMap().get("users");

        Iterator<Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();
            String username = actiontypeBucket.getKey().toString();
            usernamelist.add(username);
        }
        List<User> list = userService.listUsersByUsernames(usernamelist);

        return list;
    }
}
