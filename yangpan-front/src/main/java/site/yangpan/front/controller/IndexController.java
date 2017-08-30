package site.yangpan.front.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.yangpan.core.domain.Banner;
import site.yangpan.core.domain.User;
import site.yangpan.core.domain.enums.BannerTypeEnum;
import site.yangpan.core.domain.es.EsArticle;
import site.yangpan.core.service.BannerService;
import site.yangpan.core.service.EsArticleService;
import site.yangpan.core.vo.TagVO;

/**
 * 首页控制器
 * Created by yangpn on 2017-08-06 23:29
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    /**
     * 注入文章全文检索service
     */
    @Autowired
    private EsArticleService esArticleService;

    /**
     * 注入banner service
     */
    @Autowired
    private BannerService bannerService;

    /**
     * 默认首页
     *
     * @param order     排序方式默认new
     * @param keyword   关键词
     * @param async     异步默认false
     * @param pageIndex 当前页码
     * @param pageSize  每页条数
     * @param model     模型
     * @return
     */
    @GetMapping
    public String execute(
            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "async", required = false) boolean async,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model) {
        Page<EsArticle> page = null;
        List<EsArticle> articleList = null;
        boolean isEmpty = true;//系统初始化时，没有博客数据
        try {
            if (order.equals("hot")) {//最热查询
                Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize", "createTime");
                Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
                page = esArticleService.listHotestEsArticles(keyword, pageable);
            } else if (order.equals("new")) {//最新查询
                Sort sort = new Sort(Direction.DESC, "createTime");
                Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
                page = esArticleService.listNewestEsArticles(keyword, pageable);
            }
            isEmpty = false;
        } catch (Exception e) {
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            page = esArticleService.listEsArticles(pageable);
        }
        //当前所在页面数据列表
        articleList = page.getContent();

        //查询首页banner
        List<Banner> bannerList = bannerService.findBannerByTypeAndDisplay(BannerTypeEnum.INDEX, true);

        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("articleList", articleList);
        model.addAttribute("bannerList", bannerList);

        // 首次访问页面才加载
        if (!async && !isEmpty) {
            List<EsArticle> newArticleList = esArticleService.listTop5NewestEsArticles();
            model.addAttribute("newArticleList", newArticleList);
            List<EsArticle> hotArticleList = esArticleService.listTop5HotestEsArticles();
            model.addAttribute("hotArticleList", hotArticleList);
            List<TagVO> hotTagList = esArticleService.listTop30Tags();
            model.addAttribute("hotTagList", hotTagList);
            List<User> activeUserList = esArticleService.listTop12Users();
            model.addAttribute("activeUserList", activeUserList);
        }
        return (async == true ? "/index :: #articleListWrap" : "/index");
    }

    @GetMapping("/newest")
    public String listNewestEsArticles(Model model) {
        List<EsArticle> newest = esArticleService.listTop5NewestEsArticles();
        model.addAttribute("newest", newest);
        return "newest";
    }

    @GetMapping("/hotest")
    public String listHotestEsArticles(Model model) {
        List<EsArticle> hotest = esArticleService.listTop5HotestEsArticles();
        model.addAttribute("hotest", hotest);
        return "hotest";
    }


}
