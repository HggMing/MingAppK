package com.study.mingappk.tab3.villagebbs;

import android.view.View;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.model.bean.BbsCommentList;

import java.util.List;


public class CommentArea {

    private static final int[] commentIds = new int[]{
            R.id.comment0,
            R.id.comment1,
            R.id.comment2,
            R.id.comment3,
            R.id.comment4
    };

    private View commentMore;
    private TextView commentMoreCount;
    private View commentLayout;

    private CommentItem comment[];
    private static final int commentMaxCount = commentIds.length;

    public CommentArea(View convertView, View.OnClickListener onClickComment) {


        commentLayout = convertView.findViewById(R.id.commentArea);
        commentMore = convertView.findViewById(R.id.commentMore);
        commentMoreCount = (TextView) convertView.findViewById(R.id.commentMoreCount);

        comment = new CommentItem[commentIds.length];
        for (int i = 0; i < commentIds.length; ++i) {
            comment[i] = new CommentItem(convertView.findViewById(commentIds[i]), onClickComment, i);
        }
    }

    public void displayContentData(BbsCommentList.DataBean data) {
        List<BbsCommentList.DataBean.ListBean> commentsData = data.getList();

        if (commentsData.isEmpty()) {
            commentLayout.setVisibility(View.GONE);
        } else {
            commentLayout.setVisibility(View.VISIBLE);

            int displayCount = Math.min(commentMaxCount, commentsData.size());
            int i = 0;
            for (; i < displayCount; ++i) {
                CommentItem item = comment[i];
                item.setVisibility(View.VISIBLE);
                BbsCommentList.DataBean.ListBean comment = commentsData.get(i);
                item.setContent(comment);
            }

            for (; i < commentMaxCount; ++i) {
                comment[i].setVisibility(View.GONE);
            }
            int cnt= Integer.parseInt(data.getCnt());//评论总数
            if (cnt > commentMaxCount) {
                commentMore.setVisibility(View.VISIBLE);
                commentMoreCount.setText(String.format("查看全部%d条评论", cnt));

            } else {
                commentMore.setVisibility(View.GONE);
            }
        }
    }
}
