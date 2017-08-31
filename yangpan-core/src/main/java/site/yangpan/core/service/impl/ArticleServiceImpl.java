package site.yangpan.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.yangpan.core.domain.*;
import site.yangpan.core.domain.es.EsArticle;
import site.yangpan.core.repository.ArticleRepository;
import site.yangpan.core.service.ArticleService;
import site.yangpan.core.service.EsArticleService;

/**
 * Created by yangpn on 2017-08-06 22:52
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository blogRepository;
    @Autowired
    private EsArticleService esArticleService;


    @Transactional
    @Override
    public Article saveArticle(Article article) {
        boolean isNew = (article.getId() == null);
        EsArticle esArticle = null;
        Article returnArticle = blogRepository.save(article);

        if (isNew) {
            esArticle = new EsArticle(returnArticle);
        } else {
            esArticle = esArticleService.getEsArticleByArticleId(article.getId());
            esArticle.update(returnArticle);
        }

        esArticleService.updateEsArticle(esArticle);
        return returnArticle;
    }


    @Transactional
    @Override
    public void removeArticle(Long id) {
        blogRepository.delete(id);
        EsArticle esblog = esArticleService.getEsArticleByArticleId(id);
        esArticleService.removeEsArticle(esblog.getId());
    }


    @Override
    public Article getArticleById(Long id) {
        return blogRepository.findOne(id);
    }

    @Override
    public Page<Article> listArticlesByTitleVote(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        //Page<Article> blogs = blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title, pageable);
        String tags = title;
        Page<Article> blogs = blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title, user, tags, user, pageable);
        return blogs;
    }

    @Override
    public Page<Article> listArticlesByTitleVoteAndSort(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        Page<Article> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
        return blogs;
    }

    @Override
    public Page<Article> listArticlesByCatalog(Catalog catalog, Pageable pageable) {
        Page<Article> blogs = blogRepository.findByCatalog(catalog, pageable);
        return blogs;
    }

    @Override
    public void readingIncrease(Long id) {
        Article article = blogRepository.findOne(id);
        article.setReadSize(article.getReadSize() + 1);
        this.saveArticle(article);
    }

    @Override
    public Article createComment(Long ArticleId, String commentContent) {
        Article originalArticle = blogRepository.findOne(ArticleId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment(user, commentContent);
        originalArticle.addComment(comment);
        return this.saveArticle(originalArticle);
    }

    @Override
    public void removeComment(Long ArticleId, Long commentId) {
        Article originalArticle = blogRepository.findOne(ArticleId);
        originalArticle.removeComment(commentId);
        this.saveArticle(originalArticle);
    }

    @Override
    public Article createVote(Long ArticleId) {
        Article originalArticle = blogRepository.findOne(ArticleId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);
        boolean isExist = originalArticle.addVote(vote);
        if (isExist) {
            throw new IllegalArgumentException("该用户已经点过赞了");
        }
        return this.saveArticle(originalArticle);
    }

    @Override
    public void removeVote(Long ArticleId, Long voteId) {
        Article originalArticle = blogRepository.findOne(ArticleId);
        originalArticle.removeVote(voteId);
        this.saveArticle(originalArticle);
    }

    @Override
    public Article findNextByCurrentId(Long id) {
        return blogRepository.findNextByCurrentId(id);
    }

    @Override
    public Article findPrevByCurrentId(Long id) {
        return blogRepository.findPrevByCurrentId(id);
    }
}
