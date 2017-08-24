package site.yangpan.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yangpan.core.domain.Banner;
import site.yangpan.core.domain.enums.BannerTypeEnum;
import site.yangpan.core.repository.BannerRepository;
import site.yangpan.core.service.BannerService;

import java.util.List;

/**
 * banner业务层实现类
 * Created by yangpn on 2017-08-24 21:12
 */
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    /**
     * 根据banner类型和display查询全部banner
     *
     * @param type
     * @return
     */

    @Override
    public List<Banner> findBannerByTypeAndDisplay(BannerTypeEnum type, boolean display) {
        return bannerRepository.findByTypeAndDisplay(type, display);
    }
}
