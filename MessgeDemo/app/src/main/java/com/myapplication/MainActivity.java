package com.myapplication;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private FaceInputView faceInputView;//表情
    private LinearLayout faceInputContainer;
    private ImageView emoji;//表情
    private ListView listview;
    private EditText edt_comments;
    Boolean isShowEmotion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emoji= (ImageView) findViewById(R.id.emoji);
        listview= (ListView) findViewById(R.id.listview);
        faceInputContainer= (LinearLayout) findViewById(R.id.faceInputContainer);
        edt_comments= (EditText) findViewById(R.id.edt_comments);
        // 表情
        faceInputView = new FaceInputView(this, 1);
        faceInputContainer.addView(faceInputView);
        faceInputView.setOnClickListener(new FaceInputListenr());

        emoji.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!isShowEmotion) {
                    faceInputContainer.setVisibility(View.VISIBLE);
                    listview.setSelection(listview.getBottom());
                    listview.smoothScrollToPosition(listview.getBottom());
                } else {
                    faceInputContainer.setVisibility(View.GONE);
                    listview.setSelection(listview.getBottom());
                    listview.smoothScrollToPosition(listview.getBottom());
                }
                isShowEmotion = !isShowEmotion;
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(MainActivity.this
                                        .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });
        edt_comments.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!isShowEmotion) {
                    faceInputContainer.setVisibility(View.GONE);
                    listview.setSelection(listview.getBottom());
                    listview.smoothScrollToPosition(listview.getBottom());
                } else {
                    faceInputContainer.setVisibility(View.VISIBLE);
                    listview.setSelection(listview.getBottom());
                    listview.smoothScrollToPosition(listview.getBottom());
                }
                isShowEmotion = !isShowEmotion;
            }
        });
        listview.setSelection(listview.getBottom());
        listview.smoothScrollToPosition(listview.getBottom());
    }
    // 输入表情需要
    Html.ImageGetter imageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            int id = Integer.parseInt(source);
            @SuppressWarnings("deprecation")
            Drawable d = getResources().getDrawable(id);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    };
    class FaceInputListenr implements FaceInputView.OnFaceClickListener {

        @Override
        public void selectedFace(FaceInputView.Face face) {
            int id = face.faceId;
            if (id == R.drawable.ic_face_delete_normal)// 删除按钮
            {
                int index = edt_comments.getSelectionStart();
                if (index == 0)
                    return;
                Editable editable = edt_comments.getText();
                editable.delete(index - 1, index);// 删除最后一个字符或表情
            } else {
                edt_comments.append(Html.fromHtml("<img src='" + id + "'/>",
                        imageGetter, null));// 添加表情
            }
        }

    }
}
