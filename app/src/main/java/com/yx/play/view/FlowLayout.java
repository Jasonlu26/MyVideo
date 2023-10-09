package com.yx.play.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 */
class FlowLayout extends ViewGroup {

    private List<List<View>> lineView;    //保存每行子View列表的列表
    private int marginX = 0;    //子View间x坐标边距
    private int marginY = 0;    //子View间Y坐标边距

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("onMeasure");
        lineView = new ArrayList<>();   //初始化子View列表
        int paddingX = getPaddingLeft() + getPaddingRight();     //获取左右内边距尺寸
        int paddingY = getPaddingTop() + getPaddingBottom();     //获取上下内边距尺寸
        int width = MeasureSpec.getSize(widthMeasureSpec) - paddingX - marginX * 2;  //获取子View左右可用尺寸
        int height = MeasureSpec.getSize(heightMeasureSpec) - paddingY - marginY * 2;   //获取子View上下可用尺寸
        System.out.println("width = " + width + " , height = " + height + "  ,  paddingX = " + paddingX + " , paddingY = " + paddingY);

        List<View> list = new ArrayList<>();    //保存一行子View的列表
        lineView.add(list);
        int useWidth = 0;         //一行使用的宽度
        int maxWidth = 0;         //view中被子View使用的最大宽度，即全部行中被使用的最大宽度
        int useHeight = 0;        //每一行中最大使用的高度
        int maxHeight = 0;        //view中被子View使用的最大高度


        //获取子View数量
        int count = getChildCount();
        //遍历
        for (int i = 0; i < count; i++) {
            //获取子View
            View child = getChildAt(i);
            //获取子View布局参数
            LayoutParams layoutParams = child.getLayoutParams();

            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                    paddingX + marginX * 2, layoutParams.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                    paddingY + marginY * 2, layoutParams.height);
            //测量子View
            child.measure(childWidthMeasureSpec,childHeightMeasureSpec);

            //获取子View的测量宽高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //判断这一行是否还能存下子View，通过已使用宽度加子View宽度与view的可使用宽度比较
            if((useWidth + childWidth) >= width){
                list = new ArrayList<>();   //创建新的一行子View列表
                lineView.add(list);         //把一行子View列表保存在lineView
                //原来使用宽度的最大值与这一行使用的宽度来取最大值
                maxWidth = Math.max(maxWidth,useWidth);
                //一行结束后，更新使用的高度
                maxHeight = maxHeight + useHeight + marginY;
                //状态置零
                useWidth = 0;
                useHeight = 0;
            }
            list.add(child);   //保存子View
            //更新这一行使用的使用宽度
            useWidth = useWidth + childWidth + marginX;
            //保存这一行使用的最大高度
            useHeight = Math.max(childHeight,useHeight);

        }

        //遍历结束后，可能最后一个子View不是一行的最后一个，所以需要我们判断处理
        if(useWidth != 0 && useHeight != 0){
            maxWidth = Math.max(maxWidth,useWidth);
            maxHeight = maxHeight + useHeight;
        }

        //获取原来View的布局参数
        LayoutParams layoutParams = getLayoutParams();
        //如果使用WRAP_CONTENT，则使用我们根据子View测量的结果
        if(layoutParams.width == LayoutParams.WRAP_CONTENT){
            width = maxWidth;
        }

        //如果使用WRAP_CONTENT，则使用我们根据子View测量的结果
        if(layoutParams.height == LayoutParams.WRAP_CONTENT){
            height = maxHeight;
        }

        //保存测量结果
        setMeasuredDimension(width + paddingX + marginX * 2,
                height +paddingY + marginY * 2);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(lineView != null){
            //子View开始布局的位置
            int useHeight = getPaddingTop() + marginY;
            int useWidth = getPaddingLeft() + marginX;

            for (int i = 0; i < lineView.size(); i++) {
                List<View> list = lineView.get(i);
                if(list != null){
                    int lineUseHeight = 0;    //用来保存每一行使用的最大高度
                    for (int i1 = 0; i1 < list.size(); i1++) {
                        View child = list.get(i1);
                        int childWidth = child.getMeasuredWidth();
                        int childHeight = child.getMeasuredHeight();
//                        System.out.println("childWidth = " + childWidth + " , childHeight = " + childHeight);
                        //布局子View
                        child.layout(useWidth,useHeight,
                                useWidth + childWidth,useHeight + childHeight);
                        //更新已使用的宽度
                        useWidth += marginX + childWidth;
                        //更新这一行已使用的最大高度
                        lineUseHeight = Math.max(lineUseHeight,childHeight);
                    }
                    //布局完一行子View后，需要更新已使用的宽高
                    useWidth = getPaddingLeft() + marginX;
                    useHeight += lineUseHeight + marginY;
                }
            }
            //全部子View布局完后，清除子View的保存
            lineView.clear();
        }
    }
}
