package com.yx.play.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sccdwxxyljx.com.R;


public class MyPrivacyDialog extends AlertDialog {
    private Context context;
    private TextView agreeTv;
    public static final int ARGEEMENT_TEXT_CLICK = 1; //用户协议
    public static final int SECRET_TEXT_CLICK = 2; //隐私协议
    public static final int ARGEE_BTN_CLICK = 3; //同意按钮
    public static final int NOT_ARGEE_BTN_CLICK = 4; //不同意按钮
    private static int START_AG = 0;  //用户协议需要加颜色的开始文字位置
    private static int END_AG = 0;//结束

    private static int START_SECRET = 0;//隐私开始文字位置
    private static int END_SECRET = 0;//结束
    private OnBtnClickListener listener;
    public MyPrivacyDialog(Context context) {
        super(context);
        this.context = context;
    }

    public interface OnBtnClickListener {
        void onClick(int type);
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.listener = onBtnClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog_privacy);
        initView();
    }

    private void initView() {
        agreeTv = findViewById(R.id.userArgTv);
        String argContent = "点击查看《服务协议》和《隐私协议》，了解详细信息。";
        String serviceStr = "《服务协议》";
        String privateStr = "《隐私协议》";
        START_AG = argContent.indexOf(serviceStr);
        END_AG = START_AG + serviceStr.length();
        START_SECRET = argContent.indexOf(privateStr);
        END_SECRET = START_SECRET + privateStr.length();

        SpannableString spannableString = new SpannableString(argContent);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#338034"));
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#338034"));
        UnderlineSpan underlineSpan = new UnderlineSpan();
        UnderlineSpan underlineSpan2 = new UnderlineSpan();
        ClickableSpan userArgeeClick = new ClickableSpan() {
            //用户协议
            @Override
            public void onClick(@NonNull View view) {
                //Toast.makeText(context, "用户协议", Toast.LENGTH_SHORT).show();
                listener.onClick(ARGEEMENT_TEXT_CLICK);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(false);//去除连接下划线
                ds.setColor(Color.parseColor("#338034"));
                ds.clearShadowLayer();
            }
        };

        ClickableSpan secretClick = new ClickableSpan() {
            //隐私政策
            @Override
            public void onClick(@NonNull View view) {
                //Toast.makeText(context, "隐私政策", Toast.LENGTH_SHORT).show();
                listener.onClick(SECRET_TEXT_CLICK);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#338034"));
                ds.setUnderlineText(false);//去除连接下划线
                ds.clearShadowLayer();

            }
        };


        findViewById(R.id.agreeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(ARGEE_BTN_CLICK);
            }
        });

        findViewById(R.id.notAgreeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(NOT_ARGEE_BTN_CLICK);
            }
        });
        spannableString.setSpan(colorSpan, START_AG, END_AG, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(underlineSpan, START_AG + 1, END_AG - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(userArgeeClick, START_AG, END_AG, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        spannableString.setSpan(colorSpan2, START_SECRET, END_SECRET, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(underlineSpan2, START_SECRET + 1, END_SECRET - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(secretClick, START_SECRET, END_SECRET, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        agreeTv.setMovementMethod(LinkMovementMethod.getInstance());
        //设置点击背景色透明  解决点击时有阴影效果
        agreeTv.setHighlightColor(context.getResources().getColor(android.R.color.transparent));
        agreeTv.setText(spannableString);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }
}


