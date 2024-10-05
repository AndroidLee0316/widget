package com.pasc.lib.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pasc.business.workspace.BaseLoadingFragment;
import com.pasc.lib.demo.widget.tablayout.BottomNavigationBarSampleActivity;
import com.pasc.lib.demo.widget.tablayout.SegmentedSampleActivity;
import com.pasc.lib.router.BaseJumper;
import com.pasc.lib.widget.list.PaExpandableListView;
import com.pasc.lib.widget.toast.Toasty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C) 2019 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2019/4/10
 * UI验收版本
 */
public class UICheckFragment extends BaseLoadingFragment {
    private PaExpandableListView mPaExpandableListView;
    private List<String> mGroupList;
    private List<List<String>> mChildList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ui_check_version, container, false);
        mPaExpandableListView = rootView.findViewById(R.id.PaExpandableListView);
        initData();
        showData();
        return rootView;
    }

    private void showData() {
        mPaExpandableListView.setExpandableData(mGroupList, mChildList, new PaExpandableListView.OnGroupExpandedListener() {
            @Override
            public void onGroupExpanded(int groupPosition, int childPosition) {
                String groupName = mGroupList.get(groupPosition);
                String childName = mChildList.get(groupPosition).get(childPosition);
                switch (groupName) {
                    case "导航":
                        switch (childName) {
                            case "标题导航栏":
                                BaseJumper.jumpARouter("/Demo/Containers/PascToolbar");
                                return;
                            case "标签栏 Tab Bar":
                                startActivity(new Intent(getContext(), BottomNavigationBarSampleActivity.class));
                                return;
                            case "分段控件 Segmented Control":
                                startActivity(new Intent(getContext(), SegmentedSampleActivity.class));
                                return;
                            case "状态栏(隶属于主题，不单独开发)":
                                return;
                            default:
                                break;
                        }
                        break;
                    case "浮层Popver":
                        switch (childName) {
                            case "菜单选择":
                                BaseJumper.jumpARouter("/Demo/Containers/CatalogSelectView");
                                return;
                            case "Popup浮层弹窗":
                                BaseJumper.jumpARouter("/Demo/Others/PascPopup");
                                return;
                            default:
                                break;
                        }
                        break;
                    case "搜索栏Search Bar":
                        switch (childName) {
                            case "搜索导航":
                                BaseJumper.jumpARouter("/Demo/Containers/PascSearchBar");
                                return;
                            default:
                                break;
                        }
                        break;
                    case "按钮":
                        switch (childName) {
                            case "长按钮":
                            case "短按钮":
                                BaseJumper.jumpARouter("/Demo/Widgets/PascLoadingButton");
                                return;
                            case "底部固定按钮":
                                break;
                            case "开关":
                                BaseJumper.jumpARouter("/Demo/Widgets/PascToggleButton");
                                return;
                            default:
                                break;
                        }
                        break;
                    case "列表":
                        switch (childName) {
                            case "资讯列表":
                                break;
                            case "图片列表":
                                break;
                            case "办事指南列表":
                                break;
                            case "其他列表":
                                break;
                            default:
                                break;
                        }
                        break;
                    case "详情":
                        switch (childName) {
                            case "隶属h5(不单独开发)":
                                return;
                            default:
                                break;
                        }
                        break;
                    case "表单Form":
                        switch (childName) {
                            case "展示操作型":
                                break;
                            case "文本输入型Input":
                                break;
                            default:
                                break;
                        }
                        break;
                    case "通告栏":
                        switch (childName) {
                            case "通告栏":
                                BaseJumper.jumpARouter("/Demo/Widgets/ScrollTextView");
                                return;
                            default:
                                break;
                        }
                        break;
                    case "选择器Picker":
                        switch (childName) {
                            case "单维度选择":
                                BaseJumper.jumpARouter("/Demo/Dialogs/BottomChoiceDialogFragment");
                                return;
                            case "多维度选择":
                                BaseJumper.jumpARouter("/Demo/Dialogs/DatePickerDialogFragment");
                                return;
                            default:
                                break;
                        }
                        break;
                    case "弹窗Dialog":
                        switch (childName) {
                            case "告知型弹窗Alert":
                                BaseJumper.jumpARouter("/Demo/Dialogs/ConfirmDialogFragment");
                                return;
                            case "提交型弹窗":
                                BaseJumper.jumpARouter("/Demo/Dialogs/ChoiceDialogFragment");
                                return;
                            case "插图弹窗":
                                BaseJumper.jumpARouter("/Demo/Dialogs/InsetDialogFragment");
                                return;
                            case "权限开启型弹窗":
                                BaseJumper.jumpARouter("/Demo/Dialogs/PermissionDialogFragment2");
                                return;
                            default:
                                break;
                        }
                        break;
                    case "行动视图Action Sheet":
                        switch (childName) {
                            case "操作选项":
                                BaseJumper.jumpARouter("/Demo/Dialogs/BottomChoiceDialogFragment");
                                return;
                            case "分享":
                                BaseJumper.jumpARouter("/Demo/Dialogs/InsetDialogFragment");
                                return;
                            default:
                                break;
                        }
                        break;
                    case "消息提示Toast":
                        switch (childName) {
                            case "消息提示Toast":
                                BaseJumper.jumpARouter("/Demo/Others/Toasty");
                                return;
                            default:
                                break;
                        }
                        break;
                    case "加载Loading":
                        switch (childName) {
                            case "原生页加载(系统主动加载)":
                                break;
                            case "H5-Webview加载":
                                break;
                            case "下拉刷新":
                            case "上拉加载":
                                BaseJumper.jumpARouter("/Demo/Widgets/PaSwipeRefreshLayout");
                                return;
                            default:
                                break;
                        }
                        break;
                    case "结果页Result Page":
                        switch (childName) {
                            case "扫描结果":
                                break;
                            case "任务结果":
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }

                Toasty.init(getContext()).setMessage("暂未实现！！！").onCenter().show();
            }
        });
    }

    private void initData() {
        mChildList = new ArrayList<>();
        mGroupList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.ui_check_group)));
        for (int i = 0; i < mGroupList.size(); i++) {
            String groupName = mGroupList.get(i);
            List<String> tempList = new ArrayList<>();
            switch (groupName) {
                case "导航":
                    tempList.add("标题导航栏");
                    tempList.add("标签栏 Tab Bar");
                    tempList.add("分段控件 Segmented Control");
                    tempList.add("状态栏(隶属于主题，不单独开发)");
                    break;
                case "浮层Popver":
                    tempList.add("菜单选择");
                    tempList.add("Popup浮层弹窗");
                    break;
                case "搜索栏Search Bar":
                    tempList.add("搜索导航");
                    break;
                case "按钮":
                    tempList.add("长按钮");
                    tempList.add("短按钮");
                    tempList.add("底部固定按钮");
                    tempList.add("开关");
                    break;
                case "列表":
                    tempList.add("资讯列表");
                    tempList.add("图片列表");
                    tempList.add("办事指南列表");
                    tempList.add("其他列表");
                    break;
                case "详情":
                    tempList.add("隶属h5(不单独开发)");
                    break;
                case "表单Form":
                    tempList.add("展示操作型");
                    tempList.add("文本输入型Input");
                    break;
                case "通告栏":
                    tempList.add("通告栏");
                    break;
                case "选择器Picker":
                    tempList.add("单维度选择");
                    tempList.add("多维度选择");
                    break;
                case "弹窗Dialog":
                    tempList.add("告知型弹窗Alert");
                    tempList.add("提交型弹窗");
                    tempList.add("插图弹窗");
                    tempList.add("权限开启型弹窗");
                    break;
                case "行动视图Action Sheet":
                    tempList.add("操作选项");
                    tempList.add("分享");
                    break;
                case "消息提示Toast":
                    tempList.add("消息提示Toast");
                    break;
                case "加载Loading":
                    tempList.add("原生页加载(系统主动加载)");
                    tempList.add("H5-Webview加载");
                    tempList.add("下拉刷新");
                    tempList.add("上拉加载");
                    break;
                case "结果页Result Page":
                    tempList.add("扫描结果");
                    tempList.add("任务结果");
                    break;
                default:
                    break;
            }
            mChildList.add(tempList);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
