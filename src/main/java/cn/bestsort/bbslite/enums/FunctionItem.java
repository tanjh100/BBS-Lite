package cn.bestsort.bbslite.enums;

/**
 * @ClassName FollowEnum
 * @Description
 * @Author bestsort
 * @Date 19-9-29 下午2:52
 * @Version 1.0
 */

public enum FunctionItem{
    /**
     * COMMENT: 评论
     * TOPIC: 话题
     * USER: 用户
     * ARTICLE: 问题
     * REPLY: 回复
     */
    COMMENT,
    TOPIC,
    USER,
    ARTICLE,
    REPLY;
    public static FunctionItem getItem(String str){
        for(FunctionItem i:FunctionItem.values()){
            if (FunctionItem.valueOf(str) == i){
                return i;
            }
        }
        return null;
    }
    public static FunctionItem getByCode(Byte code){
        byte byt = 0;
        for(FunctionItem i:FunctionItem.values()){
            if(byt == code) {
                return i;
            }
            byt++;
        }
        return null;
    }
    public static Byte getCode(FunctionItem item){
        byte byt = 0;
        for(FunctionItem i:FunctionItem.values()){
            if(i == item) {
                return byt;
            }
            byt++;
        }
        return null;
    }
}
