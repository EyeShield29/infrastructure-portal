package com.infrastructure.portal.entity.po.process;
/**
 * TODO <br/>
 * <br>=
 * ============================== <br>
 * 团队：CSCW <br>
 * 系统：TODO <br>
 * 研发：Junfa <br>
 * 创建时间：2017年4月3日 下午4:13:43 <br>=
 * ==============================
 */
public enum PeriodEnum {

    FIRST("1", "立项"), SECOND("2", "设计"), THIRD("3", "预算清单"), FOURTH("4", "招标"), FIFTH("5", "施工监管"), SIXTH(
            "6", "结算"), SEVENTH("7", "已完成");

    private String key;
    private String value;

    // 自定义的构造函数，参数数量，名字随便自己取
    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
    private PeriodEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // 重新toString方法，默认的toString方法返回的就是枚举变量的名字，和name()方法返回值一样
    @Override
    public String toString() {
        return this.key + ":" + this.value;

    }
    
    
}
