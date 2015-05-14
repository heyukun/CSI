package com.xc.csi.activity;

/**
 * Created by heyukun on 2015/5/14.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.xc.csi.R;
import com.xc.csi.adapter.XCFragmentAdapter;
import com.xc.csi.fragment.MainTabFragment;
import com.xc.csi.fragment.MapTabFragment;
import com.xc.csi.fragment.MoreTabFragment;

import java.util.ArrayList;
import java.util.List;


public class MainTabActivity extends FragmentActivity implements
        ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {
    //定义FragmentTabHost对象
    private FragmentTabHost mTabHost;

    //定义一个布局
    private LayoutInflater layoutInflater;

    private ViewPager vp;

    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {MainTabFragment.class, MapTabFragment.class, MoreTabFragment.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tab_home_btn,R.drawable.tab_message_btn,R.drawable.tab_more_btn};

    //Tab选项卡的文字
    private String mTextviewArray[] = {"首页", "地图", "更多"};

    private List<Fragment> list = new ArrayList<Fragment>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);

        initView();

        initPage();

        //第一次启动时，存在第一个item的背景错误的bug，这样可以暂时解决
        vp.setCurrentItem(1);
        vp.setCurrentItem(0);
    }

    /**
     * 初始化组件
     */
    private void initView(){

        vp = (ViewPager) findViewById(R.id.pager);
        vp.setOnPageChangeListener(this);
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.pager);
        mTabHost.setOnTabChangedListener(this);
        //得到fragment的个数
        int count = fragmentArray.length;

        for(int i = 0; i < count; i++){
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
        }
    }

    /**
     * 初始化Fragment
     */
    private void initPage() {
        MainTabFragment fragment1 = new MainTabFragment();
        MapTabFragment fragment2 = new MapTabFragment();
        MoreTabFragment fragment3 = new MoreTabFragment();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        vp.setAdapter(new XCFragmentAdapter(getSupportFragmentManager(), list));
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mTabHost.setCurrentTab(arg0);
        widget.setDescendantFocusability(oldFocusability);
        mTabHost.getTabWidget().getChildAt(arg0)
                .setBackgroundResource(R.drawable.selector_tab_background);
    }

    @Override
    public void onTabChanged(String tabId) {
        int position = mTabHost.getCurrentTab();
        vp.setCurrentItem(position);
    }

}