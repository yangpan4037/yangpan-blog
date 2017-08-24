package site.yangpan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yangpan.core.domain.Banner;
import site.yangpan.core.domain.enums.BannerTypeEnum;

import java.util.List;

/**
 * banner持久层接口
 * Created by yangpn on 2017-08-24 21:03
 */
public interface BannerRepository extends JpaRepository<Banner, Long> {

    /**
     * 根据类型和是否显示查询全部banner
     *
     * @param type
     * @return
     */
    List<Banner> findByTypeAndDisplay(BannerTypeEnum type, boolean display);
}
