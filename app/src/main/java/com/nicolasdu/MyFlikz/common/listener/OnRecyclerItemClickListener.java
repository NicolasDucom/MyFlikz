package com.nicolasdu.MyFlikz.common.listener;

import android.view.View;

/**
 * Created by Nicolas on 2/20/2016.
 */
public interface OnRecyclerItemClickListener {
    void onClick(View view, int position, boolean isLongClick);
}
