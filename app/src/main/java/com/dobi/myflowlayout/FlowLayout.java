package com.dobi.myflowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2018/7/31.
 */

public class FlowLayout extends ViewGroup {

    private Line mCurrentLine;//当前行
    private int useWidth = 0;//当前行使用的宽度
    private List<Line> mLines = new ArrayList<Line>();//维护所有行的集合

    private int horizontolSpacing = 26;//两个TextView之间的间距26个像素
    private int verticalSpacing = 26;//两个TextView之间的间距26个像素

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //因为可能不止一次测量，所以进来得清空
        mCurrentLine = null;
        useWidth=0;
        mLines.clear();


        //获取当前父容器(FlowLayout)的mode 和 size
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //当前父容器(FlowLayout)宽度和高度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //为了测量每个孩子需要指定每个孩子的测量规则
        //因为子view是textview，并且测量模式都是wrap_content即所以计算子view模式
        int childWidthMode = withMode==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:withMode;
        int childHeightMode = heightMode==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:heightMode;
        //得到子view测量规则
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width,childWidthMode);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,childHeightMode);

        mCurrentLine = new Line();//创建了第一行

        //上面做了那么多就是为了下面开始测量子孩子
        for(int i=0;i<getChildCount();i++){
            View child = getChildAt(i);
            //测量每个孩子
            child.measure(childWidthMeasureSpec,childHeightMeasureSpec);

            //得到每个孩子的测量宽度
            int measuredWidth = child.getMeasuredWidth();
            //当前行使用的宽度增加
            useWidth+=measuredWidth;
            //当前使用行与父控件比较，看是否需要换行
            if(useWidth<=width){
               mCurrentLine.addChild(child);//当前行可以添加孩子
                useWidth += horizontolSpacing; //当前使用行宽度加上间距
                if(useWidth>width){//超过宽度，则也需要换行
                    //换行
                    newLine();
                }

            }else {//需要换行
                    if(mCurrentLine.getChildCount()<1 ){
                        mCurrentLine.addChild(child);  //保证当前行里面最少有一个孩子
                    }
                newLine();
            }

        }
        //这里是保证最后一行被添加了
        if(!mLines.contains(mCurrentLine)){
            mLines.add(mCurrentLine);
        }

        //计算总高度
        int totalheight = 0;
        for(Line line:mLines){
            totalheight +=line.getHeight();  //每行的高度
        }
        totalheight +=verticalSpacing*(mLines.size()-1); //还得加上垂直间距


       // setMeasuredDimension(width,totalheight);  //万一总高度还没有height高，就会出问题
        //totalheight来匹配测量规则
        setMeasuredDimension(width,resolveSize(totalheight,heightMeasureSpec));


    }


    /**
     * 分配每个孩子的位置
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for(int i=0;i<mLines.size();i++){
            Line line =mLines.get(i);
            line.layout(l,t);   //给每一行分配位置-----------------然后交给每一行去给孩子分配位置
            t+=line.getHeight()+verticalSpacing;  //分配完后top加上一行高度和行间距
        }

    }


    /**
     * 新建一行----目的换行
     */
    private void newLine() {
        //换行之前必须先把已经满行的那一行给添加到mLines行集合
        mLines.add(mCurrentLine);//记录之前的行
        mCurrentLine = new Line();//创建新的一行
        useWidth=0;    //换行之后初始化
    }

    /**
     * 行的类
     */
    private class  Line{

        int height = 0;  //当前行高度

        //记录每行的子view
        private List<View> children = new ArrayList<View>();
        //当前行添加孩子即TextView
        public void addChild(View child) {
            children.add(child);
            //高度取当前行最高的孩子高度
            if(child.getMeasuredHeight()>height){
                height = child.getMeasuredHeight();
            }


        }

        /**
         * 返回孩子的数量
         * @return
         */
        public int getChildCount() {
            return  children.size();
        }

        public int getHeight() {
            return height;
        }

        /**
         * 给每行孩子分配位置----摆放
         * @param l
         * @param t
         */
        public void layout(int l, int t) {

            for(int i=0;i<children.size();i++){
                View child = children.get(i);
                //此处没有显示，所以只能用getMeasuredWidth()，不能用getWidth，getWidth显示大小
               child.layout(l,t,l+child.getMeasuredWidth(),t+child.getMeasuredHeight());   //给每个子孩子分配位置
              //  child.layout(l,t,l+getMeasuredWidth(),t+getMeasuredHeight());   //瀑布流形式
                l+=child.getMeasuredWidth();
                l+=horizontolSpacing;
            }
        }
    }


}
