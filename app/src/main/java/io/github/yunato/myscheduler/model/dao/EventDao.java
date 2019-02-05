package io.github.yunato.myscheduler.model.dao;

import android.content.Context;

import java.util.List;

import io.github.yunato.myscheduler.model.item.EventInfo;

abstract class EventDao {
    private final Context context;
    private final MyPreferences myPreferences;

    EventDao(Context context){
        this.context = context;
        this.myPreferences = new MyPreferences(context);
    }

    /**
     * プリファレンスから値を取得する
     * @param key       キー
     * @return          値
     */
    public String getValueFromPref(String key){
        return myPreferences.getValue(key);
    }

    /**
     * プリファレンスへ値を設定する
     * @param key       キー
     * @param value     値
     */
    public void setValueToPref(String key, String value){
        myPreferences.setValue(key, value);
    }

    public abstract List<String> insertEventItems(List<EventInfo.EventItem> eventItems);

    public abstract List<EventInfo.EventItem> getEventItems();
}