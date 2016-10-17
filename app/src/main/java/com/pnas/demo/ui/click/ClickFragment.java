package com.pnas.demo.ui.click;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/***********
 * @author pans
 * @date 2016/9/30
 * @describ
 */
public class ClickFragment extends BaseFragment {

    @BindView(R.id.click_fragment_tv)
    TextView mTextView;

    public ClickFragment() {
    }

    public static ClickFragment newInstance(int number) {
        ClickFragment fragment = new ClickFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int number = getArguments().getInt("number");
        log("onCreateView -- " + number);
        View inflate = inflater.inflate(R.layout.fragment_click, container, false);
        ButterKnife.bind(this, inflate);
        mTextView.setText("Fragment" + number);
        return inflate;
    }
}
