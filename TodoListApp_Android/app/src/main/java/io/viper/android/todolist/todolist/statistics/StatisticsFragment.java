/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.viper.android.todolist.todolist.statistics;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.lhyz.android.todolist.R;
import io.lhyz.android.todolist.databinding.StatisticsFragBinding;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Main UI for the statistics screen.
 * <p/>
 * Note that this Fragment is not implementing {@link StatisticsContract.View},
 * but the {@link StatisticsViewModel} is.
 */
public class StatisticsFragment extends Fragment {

    private StatisticsPresenter mPresenter;

    private StatisticsFragBinding mViewDataBinding;

    private StatisticsViewModel mStatisticsViewModel;

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    public void setPresenter(@NonNull StatisticsPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(
                inflater, R.layout.statistics_frag, container, false);
        View root = mViewDataBinding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewDataBinding.setStats(mStatisticsViewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public void setViewModel(StatisticsViewModel statisticsViewModel) {
        mStatisticsViewModel = statisticsViewModel;
    }

    public boolean isActive() {
        return isAdded();
    }
}
