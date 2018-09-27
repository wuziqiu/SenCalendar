package com.wuziqiu.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SenCalendar extends LinearLayout{

    private ImageView pre_iv;
    private ImageView next_iv;
    private TextView date_tv;
    private GridView calendar_gv;
    private Calendar curData = Calendar.getInstance();

    public SenCalendar(Context context) {
        this(context, null);
    }

    public SenCalendar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SenCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }


    private void initControl(Context context){
        bindControl(context);
        bindControlEvent();
        renderCalendar();
    }

    private void bindControl(Context context){

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(com.wuziqiu.calendar.R.layout.calendar,this);
        pre_iv = findViewById(com.wuziqiu.calendar.R.id.pre_iv);
        next_iv = findViewById(com.wuziqiu.calendar.R.id.next_iv);
        date_tv = findViewById(com.wuziqiu.calendar.R.id.date_tv);
        calendar_gv = findViewById(com.wuziqiu.calendar.R.id.calendar_gv);

    }

    private void bindControlEvent(){
        pre_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                curData.add(Calendar.MONTH,-1);
                renderCalendar();

            }
        });

        next_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                curData.add(Calendar.MONTH,+1);
                renderCalendar();
            }
        });
    }

    private void renderCalendar(){

        //curData.clear();//清缓存，不然获取每月最后一天有问题！
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy", Locale.CHINESE);
        date_tv.setText(sdf.format(curData.getTime()));

        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) curData.clone();


        calendar.set(Calendar.DAY_OF_MONTH, 1);//设为本月第一天
        int preDays = calendar.get(Calendar.DAY_OF_WEEK)-1;
        int line = (calendar.getActualMaximum(Calendar.DATE) + preDays)/7;//日历行数
        int surplus = (calendar.getActualMaximum(Calendar.DATE) + preDays)%7;//剩余的天数
        if ((surplus != 0)) {
            line++;
        }
        calendar.add(Calendar.DAY_OF_WEEK, -preDays);//设为日历第一行的第一天

        int maxCellCount = 7*line;//日历格子数
        while (cells.size()<maxCellCount){
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendar_gv.setAdapter(new CalendarAdapter(getContext(), cells));
    }

    private class CalendarAdapter extends ArrayAdapter<Date>{

        LayoutInflater inflater;
        CalendarAdapter(@NonNull Context context, ArrayList<Date> days) {
            super(context, com.wuziqiu.calendar.R.layout.calendar_day, days);
            inflater = LayoutInflater.from(context);
        }



        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Date date = getItem(position);
            if(convertView == null){
                convertView = inflater.inflate(com.wuziqiu.calendar.R.layout.calendar_day, parent, false);
            }

            int day = date.getDate();
            ((TextView)convertView).setText(String.valueOf(day));


            Calendar calendar = (Calendar) curData.clone();
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            //本月
            if(date.getMonth() == curData.getTime().getMonth()){
                ((DayTextView)convertView).setTextColor(Color.parseColor("#444444"));
                convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            //非本月
            else {
                ((DayTextView)convertView).setTextColor(Color.parseColor("#aaaaaa"));
                convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
            }

            //当天

            Date now = new Date();
            if(now.getDate() == date.getDate() && now.getMonth() == date.getMonth()
                    && now.getYear() == date.getYear()) {
                ((DayTextView)convertView).setTextColor(Color.RED);
                ((DayTextView)convertView).isToday = true;
            }


            return convertView;
        }
    }

}
