package com.python.cat.studyview.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.python.cat.studyview.fragment.ItemFragment;

import java.util.ArrayList;
import java.util.List;

public class PageItemAdapter extends FragmentPagerAdapter {

    private final int count;
    private final int size;
    private final List<Fragment> fragments = new ArrayList<>();

    public PageItemAdapter(@NonNull FragmentManager fm, List<String> stringList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        stringList.forEach(ss -> {
            ItemFragment itemFragment = ItemFragment.newInstance(ss);
            fragments.add(itemFragment);
        });
        this.count = stringList.size() * 10;
        this.size = stringList.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position % size);
    }

    @Override
    public int getCount() {
        return size;
    }
}
