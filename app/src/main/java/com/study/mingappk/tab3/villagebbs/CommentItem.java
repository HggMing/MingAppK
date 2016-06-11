package com.study.mingappk.tab3.villagebbs;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.model.bean.BbsCommentList;

import java.util.Date;

import butterknife.Bind;

class CommentItem {
    private TextView comment;
    private TextView name;
    private TextView time;
    private View layout;

    public CommentItem(View convertView, View.OnClickListener onClickComment, int i) {
        layout = convertView;
//**        layout.setOnClickListener(onClickComment);
        name = (TextView) convertView.findViewById(R.id.comment_uname);
        time = (TextView) convertView.findViewById(R.id.comment_ctime);
        comment = (TextView) convertView.findViewById(R.id.comment_content);
//        comment.setMovementMethod(LongClickLinkMovementMethod.getInstance());
//**        comment.setOnClickListener(onClickComment);
//        comment.setOnLongClickListener(DialogCopy.getInstance());
    }

    public void setContent(BbsCommentList.DataBean.ListBean mList) {
//        layout.setTag(MaopaoListBaseFragment.TAG_COMMENT, commentData);
//        comment.setTag(MaopaoListBaseFragment.TAG_COMMENT, commentData);
//        comment.setTag(MaopaoListBaseFragment.TAG_COMMENT_TEXT, commentData.content);
        //评论人姓名
        String uname = mList.getUname();
        if(uname.isEmpty()){
            //若用户名为空，显示手机号，中间四位为*
            String iphone = mList.getUser_tel();
            uname= iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
        }
        name.setText(uname);
        //评论时间
        String date = mList.getCtime();
        if (date != null) {
            String showTime = BaseTools.getTimeFormatText(new Date(Long.valueOf(date + "000")));
            time.setText(showTime);
        } else {
            time.setText("");
        }
        //评论内容
        String commentContent = mList.getConts();
        comment.setText(commentContent);
    }

    public void setVisibility(int visibility) {
        layout.setVisibility(visibility);
    }
}
