package com.study.mingappk.common.utils.pinyin;



import com.study.mingappk.model.bean.FriendList;

import java.util.Comparator;

/**
 *用来对List中的数据根据A-Z进行排序，前面两个if判断主要是将不是以汉字开头的数据放在前面（可修改放在后面）
 */
public class PinyinComparator implements Comparator<FriendList.DataBean.ListBean> {

	@Override
	public int compare(FriendList.DataBean.ListBean lhs, FriendList.DataBean.ListBean rhs) {
		if (lhs.getSortLetters().equals("@") || rhs.getSortLetters().equals("#")) {
			return 1;
		} else if (lhs.getSortLetters().equals("#") || rhs.getSortLetters().equals("@")) {
			return -1;
		} else {
			return lhs.getSortLetters().compareTo(rhs.getSortLetters());
		}
	}
}
