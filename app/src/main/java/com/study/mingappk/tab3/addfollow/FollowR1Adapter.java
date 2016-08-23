package com.study.mingappk.tab3.addfollow;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.A4Town;
import com.study.mingappk.model.bean.A5Village;
import com.study.mingappk.model.databean.FollowTreeData;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.baseactivity.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ming on 2016/5/26.
 */
public class FollowR1Adapter extends BaseRecyclerViewAdapter<FollowTreeData, RecyclerView.ViewHolder> {


    /**
     * 展开隐藏列表
     */
    public interface ItemDataClickListener {
        public void onExpandChildren(FollowTreeData itemData, ParentViewHolder holder);

        public void onHideChildren(FollowTreeData itemData);
    }

    /**
     * 滚动到指定项
     */
    public interface OnScrollToListener {
        public void scrollTo(int position);
    }

    private OnScrollToListener onScrollToListener;

    public void setOnScrollToListener(OnScrollToListener onScrollToListener) {
        this.onScrollToListener = onScrollToListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case FollowTreeData.ITEM_TYPE_PARENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_addfollow_tree_parent, parent, false);
                return new ParentViewHolder(view);
            case FollowTreeData.ITEM_TYPE_CHILD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_addfollow_tree_child, parent, false);
                return new ChildViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_addfollow_tree_parent, parent, false);
                return new ChildViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case FollowTreeData.ITEM_TYPE_PARENT:
                ParentViewHolder pHolder = (ParentViewHolder) holder;
                pHolder.bindView(mList.get(position), imageClickListener);
                break;
            case FollowTreeData.ITEM_TYPE_CHILD:
                final ChildViewHolder cHolder = (ChildViewHolder) holder;
                cHolder.bindView(mList.get(position), position);
                cHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(cHolder.itemLayout,position);
                    }
                });
                break;
            default:
                break;
        }

    }

    private ItemDataClickListener imageClickListener = new ItemDataClickListener() {
        @Override
        public void onExpandChildren(final FollowTreeData itemData, ParentViewHolder holder) {
            int position = getCurrentPosition(itemData.getUuid());
            switch (itemData.getTreeDepth()) {
                case 0:
                    showTree1(itemData, position, holder);//显示二级列表
                    break;
                case 1:
                    showTree2(itemData, position, holder);//显示第3级列表
                    break;
            }
        }

        @Override
        public void onHideChildren(FollowTreeData itemData) {
            int position = getCurrentPosition(itemData.getUuid());
            List<FollowTreeData> children = itemData.getChildren();
            if (children == null) {
                return;
            }
            removeAll(position + 1, getChildrenCount(itemData) - 1);
            if (onScrollToListener != null) {
                onScrollToListener.scrollTo(position);
            }
            itemData.setChildren(null);
        }
    };

    private void showTree1(final FollowTreeData itemData, final int position, final ParentViewHolder holder) {
        String auth = Hawk.get(APP.USER_AUTH);
        String countryid = itemData.getCountry_id();
        MyServiceClient.getService()
                .getCall_Add4(auth, countryid)
                .enqueue(new Callback<A4Town>() {
                    @Override
                    public void onResponse(Call<A4Town> call, Response<A4Town> response) {
                        if (response.isSuccessful()) {
                            A4Town a4Town = response.body();
                            if (a4Town != null) {
                                if (a4Town.getErr() == 0) {
                                    List<FollowTreeData> children = new ArrayList<>();
                                    for (int i = 0; i < a4Town.getData().size(); i++) {
                                        FollowTreeData followTreeData = new FollowTreeData();
                                        followTreeData.setType(FollowTreeData.ITEM_TYPE_PARENT);
                                        followTreeData.setTreeDepth(1);
                                        followTreeData.setParent_name(a4Town.getData().get(i).getTown_name());
                                        followTreeData.setTown_id(a4Town.getData().get(i).getTown_id());
                                        children.add(followTreeData);
                                    }
                                    holder.count.setText(String.format("(%s)",children.size()));
                                    addAll(children, position + 1);// 插入到点击点的下方
                                    itemData.setChildren(children);
                                    if (onScrollToListener != null) {
                                        onScrollToListener.scrollTo(position + 1);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<A4Town> call, Throwable t) {

                    }
                });
    }

    private void showTree2(final FollowTreeData itemData, final int position, final ParentViewHolder holder) {
        String auth = Hawk.get(APP.USER_AUTH);
        String townid = itemData.getTown_id();
        MyServiceClient.getService().getCall_Add5(auth, townid)
                .enqueue(new Callback<A5Village>() {
                    @Override
                    public void onResponse(Call<A5Village> call, Response<A5Village> response) {
                        if (response.isSuccessful()) {
                            A5Village a5Village = response.body();
                            if (a5Village != null) {
                                if (a5Village.getErr() == 0) {
                                    List<FollowTreeData> children = new ArrayList<FollowTreeData>();
                                    for (int i = 0; i < a5Village.getData().size(); i++) {
                                        FollowTreeData followTreeData = new FollowTreeData();
                                        followTreeData.setType(FollowTreeData.ITEM_TYPE_CHILD);
                                        followTreeData.setTreeDepth(2);
                                        followTreeData.setChild_name(a5Village.getData().get(i).getVillage_name());
                                        followTreeData.setVillage_id(a5Village.getData().get(i).getVillage_id());
                                        children.add(followTreeData);
                                    }
                                    holder.count.setText(String.format("(%s)", children.size()));
                                    addAll(children, position + 1);// 插入到点击点的下方
                                    itemData.setChildren(children);
                                    if (onScrollToListener != null) {
                                        onScrollToListener.scrollTo(position + 10);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<A5Village> call, Throwable t) {

                    }
                });
    }

    public void addAll(List<FollowTreeData> list, int position) {
        mList.addAll(position, list);
        notifyItemRangeInserted(position, list.size());
    }

    protected void removeAll(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            mList.remove(position);
        }
        notifyItemRangeRemoved(position, itemCount);
    }

    private int getChildrenCount(FollowTreeData item) {
        List<FollowTreeData> list = new ArrayList<>();
        printChild(item, list);
        return list.size();
    }

    private void printChild(FollowTreeData item, List<FollowTreeData> list) {
        list.add(item);
        if (item.getChildren() != null) {
            for (int i = 0; i < item.getChildren().size(); i++) {
                printChild(item.getChildren().get(i), list);
            }
        }
    }

    protected int getCurrentPosition(String uuid) {
        for (int i = 0; i < mList.size(); i++) {
            if (uuid.equalsIgnoreCase(mList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    static class ParentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.expand)
        ImageView expand;
        @Bind(R.id.text)
        TextView text;
        @Bind(R.id.count)
        TextView count;
        @Bind(R.id.item_layout_p)
        RelativeLayout itemLayout;

        ParentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(final FollowTreeData itemData, final ItemDataClickListener imageClickListener) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) expand.getLayoutParams();
            int itemMargin = 40;
            params.leftMargin = itemMargin * itemData.getTreeDepth();
            expand.setLayoutParams(params);

            text.setText(itemData.getParent_name());

            if (itemData.isExpand()) {
                expand.setRotation(45);
                List<FollowTreeData> children = itemData.getChildren();
                if (children != null) {
                    count.setText(String.format("(%s)", itemData.getChildren().size()));
                }
                count.setVisibility(View.VISIBLE);
            } else {
                expand.setRotation(0);
                count.setVisibility(View.GONE);
            }
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageClickListener != null) {
                        if (itemData.isExpand()) {
                            imageClickListener.onHideChildren(itemData);
                            itemData.setExpand(false);
                            rotationExpandIcon(45, 0);
                            count.setVisibility(View.GONE);
                        } else {
                            imageClickListener.onExpandChildren(itemData, ParentViewHolder.this);
                            itemData.setExpand(true);
                            rotationExpandIcon(0, 45);
                            count.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void rotationExpandIcon(float from, float to) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
                valueAnimator.setDuration(150);
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        expand.setRotation((Float) valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        }
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.text)
        TextView text;
        @Bind(R.id.item_layout)
        RelativeLayout itemLayout;

        ChildViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(final FollowTreeData itemData, final int position) {
            int itemMargin = 40;
            int offsetMargin = 10;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
            params.leftMargin = itemMargin * itemData.getTreeDepth() + offsetMargin;
            image.setLayoutParams(params);
            text.setText(itemData.getChild_name());
        }
    }
}
