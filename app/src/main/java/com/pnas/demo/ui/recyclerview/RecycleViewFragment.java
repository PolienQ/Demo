package com.pnas.demo.ui.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseFragment;

/***********
 * @author pans
 * @date 2016/8/5
 * @describ
 */
public class RecycleViewFragment extends BaseFragment {

    private View mRootView;

    public RecycleViewFragment() {
    }

    public static RecycleViewFragment getInstance(int position) {
        RecycleViewFragment fragment = new RecycleViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_recycle_view, container, false);
        int position = getArguments().getInt("position", 0);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}
