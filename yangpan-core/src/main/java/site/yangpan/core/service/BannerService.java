package site.yangpan.core.service;

import site.yangpan.core.domain.Banner;
import site.yangpan.core.domain.enums.BannerTypeEnum;

import java.util.List;

/**
 * banner业务层实现类
 * Created by yangpn on 2017-08-24 21:09
 */
public interface BannerService {

    /**
     * 根据banner类型和display查询全部banner
     *
     * @return
     */
    List<Banner> findBannerByTypeAndDisplay(BannerTypeEnum type,boolean display);
}
