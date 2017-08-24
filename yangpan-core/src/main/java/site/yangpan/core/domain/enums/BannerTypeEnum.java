package site.yangpan.core.domain.enums;

/**
 * banner 枚举
 * Created by yangpn on 2017-08-24 20:51
 */
public enum BannerTypeEnum {

    INDEX(0, "首页"),
    MEMBER(1, "个人中心"),
    ARTICLE(2, "文章中心");

    //类型
    private int type;

    //名称
    private String name;

    BannerTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
