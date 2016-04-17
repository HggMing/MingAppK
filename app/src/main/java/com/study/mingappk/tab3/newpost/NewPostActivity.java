package com.study.mingappk.tab3.newpost;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.study.mingappk.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPostActivity extends AppCompatActivity {

    @Bind(R.id.edit)
    EditText edit;
    @Bind(R.id.gridView)
    AutoHeightGridView gridView;
    @Bind(R.id.popEmoji)
    ImageView popEmoji;
    @Bind(R.id.popPhoto)
    ImageView popPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.popEmoji, R.id.popPhoto})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.popEmoji:
                break;
            case R.id.popPhoto:
                break;
        }
    }
}
