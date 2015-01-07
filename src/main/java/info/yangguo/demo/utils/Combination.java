package info.yangguo.demo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合工具类，该组合类数据不会重复取
 * 如果元数据为{1，2，3}，不会出现11，22，33这种组合情况
 */
public class Combination {
    private List<List> lists = new ArrayList<>();

    /**
     * @param args   需要组合的元数据
     * @param number 多少个为一个组
     * @return List 排好序的对象
     */
    public List combine(Object[] args, int number) {
        if (null == args || args.length == 0 || number <= 0 || number > args.length)
            throw new IllegalArgumentException();
        Object[] tmp = new Object[number];//辅助空间，保存待输出组合数
        getCombination(args, number, 0, tmp, 0);
        return lists;
    }


    private void getCombination(Object[] args, int number, int begin, Object[] tmp, int index) {
        List list1 = new ArrayList();
        if (number == 0) {
            //如果够n个数了，输出tmp数组
            for (int i = 0; i < index; i++) {
                list1.add(tmp[i]);
            }
            lists.add(list1);
            return;
        }

        for (int i = begin; i < args.length; i++) {
            tmp[index] = args[i];
            getCombination(args, number - 1, i + 1, tmp, index + 1);
        }
    }
}
