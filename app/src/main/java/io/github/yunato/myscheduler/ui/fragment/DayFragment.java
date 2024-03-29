package io.github.yunato.myscheduler.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.yunato.myscheduler.R;
import io.github.yunato.myscheduler.model.entity.EventItem;
import io.github.yunato.myscheduler.model.repository.EventItemRepository;
import io.github.yunato.myscheduler.ui.activity.MainDrawerActivity;
import io.github.yunato.myscheduler.ui.adapter.DividerItemDecoration;
import io.github.yunato.myscheduler.ui.adapter.MyPlanRecyclerViewAdapter;

public class DayFragment extends Fragment {

    private OnSelectedEventListener mListener = null;

    /**
     * コンストラクタ
     */
    public DayFragment() {
    }

    /**
     * インスタンスの生成
     * @return CalendarFragment インスタンス
     */
    @SuppressWarnings("unused")
    public static DayFragment newInstance(OnSelectedEventListener listener) {
        DayFragment fragment = new DayFragment();
        fragment.setListener(listener);
        return fragment;
    }

    private void setListener(OnSelectedEventListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = EventItemRepository.getEventItems().size() != 0 ?
                inflater.inflate(R.layout.fragment_day_plan_list, container, false) :
                inflater.inflate(R.layout.fragment_day_plan_list_no_item, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.addItemDecoration(new DividerItemDecoration(context));
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(
                    new MyPlanRecyclerViewAdapter(EventItemRepository.getEventItems(), mListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((MainDrawerActivity) context).onFragmentAttached(
                EventItemRepository.convertDateToString(EventItemRepository.getTimeInMillis()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Activity へのコールバック用
     */
    public interface OnSelectedEventListener {
        void onSelectedEvent(EventItem item, View view);
    }
}
