package io.github.yunato.myscheduler.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import io.github.yunato.myscheduler.R;
import io.github.yunato.myscheduler.ui.activity.MainDrawerActivity;

public class CalendarFragment extends Fragment {

    private OnSelectedDateListener mListener = null;

    /** コンストラクタ */
    public CalendarFragment() {}

    /**
     * インスタンスの生成
     * @return CalendarFragment インスタンス
     */
    @SuppressWarnings("unused")
    public static CalendarFragment newInstance(OnSelectedDateListener listener) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setListener(listener);
        return fragment;
    }

    private void setListener (OnSelectedDateListener listener){
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view,
                                            int year,
                                            int month,
                                            int dayOfMonth) {
                mListener.onSelectedDate(year, month, dayOfMonth);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainDrawerActivity) context).onFragmentAttached(R.string.title_calendar);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Activity へのコールバック用
     */
    public interface OnSelectedDateListener {
        void onSelectedDate(int year, int month, int dayOfMonth);
    }
}
