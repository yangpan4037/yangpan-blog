package site.yangpan.core.domain;

import org.hibernate.validator.constraints.NotEmpty;
import site.yangpan.core.domain.enums.BannerTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * banner实体类
 * Created by yangpn on 2017-08-24 20:34
 */
@Entity//实体
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增长策略
    private Long id;

    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 30)
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "链接不能为空")
    @Size(min = 2, max = 255)
    @Column(nullable = false)
    private String url;

    @NotEmpty(message = "图片不能为空")
    @Size(min = 2, max = 255)
    @Column(nullable = false)
    private String pic;

    @NotEmpty(message = "类型不能为空")
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private BannerTypeEnum type;

    @NotEmpty(message = "是否显示不能为空")
    @Column(nullable = false)
    private boolean display;


    @Column(nullable = false)
    @org.hibernate.annotations.CreationTimestamp // 由数据库自动创建时间
    private Timestamp createTime;


    @NotEmpty(message = "排序不能为空")
    @Column(nullable = false)
    private Float sort;

    protected Banner() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setType(BannerTypeEnum type) {
        this.type = type;
    }

    public BannerTypeEnum getType() {

        return type;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Float getSort() {
        return sort;
    }

    public void setSort(Float sort) {
        this.sort = sort;
    }
}
