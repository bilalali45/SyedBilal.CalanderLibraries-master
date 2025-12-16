package com.syedbilalali.customcalanderview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.syedbilalali.customcalendarview.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarCustomViewV2 extends LinearLayout {

    private static final int MAX_CALENDAR_COLUMN = 42;

    private Context context;
    private GridView calendarGridView;
    private TextView currentDate;
    private ImageView previousButton, nextButton;

    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private SimpleDateFormat formatter;

    private GridAdapterV2 mAdapter;

    private ArrayList<EventObjectsSecond> dayValueData = new ArrayList<>();
    private ArrayList<EventObjects> allEvents = new ArrayList<>();
    private ArrayList<EventObjectsTime> listDaysRate = new ArrayList<>();

    public static String langaugeCode = "ar";
    public static NumberFormat numberFormat;

    public CalendarCustomViewV2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        loadLanguage();
        initView();
        initAdapter();
        setListeners();

        updateCalendar();
    }

    // ---------------- LANGUAGE ----------------
    private void loadLanguage() {
        numberFormat = NumberFormat.getInstance(new Locale(langaugeCode));
        formatter = new SimpleDateFormat("MMMM yyyy",
                langaugeCode.equals("ar") ? new Locale("ar") : Locale.ENGLISH);
    }

    // ---------------- INIT UI ----------------
    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.calendar_layout, this, true);

        calendarGridView = findViewById(R.id.calendar_grid);
        currentDate = findViewById(R.id.display_current_date);
        previousButton = findViewById(R.id.previous_month);
        nextButton = findViewById(R.id.next_month);
    }

    // ---------------- INIT ADAPTER (ONLY ONCE) ----------------
    private void initAdapter() {
        mAdapter = new GridAdapterV2(context, dayValueData, cal, allEvents);
        calendarGridView.setAdapter(mAdapter);
    }

    // ---------------- LISTENERS ----------------
    private void setListeners() {

        previousButton.setOnClickListener(v -> {
            cal.add(Calendar.MONTH, -1);
            updateCalendar();
        });

        nextButton.setOnClickListener(v -> {
            cal.add(Calendar.MONTH, 1);
            updateCalendar();
        });

        calendarGridView.setOnItemClickListener((parent, view, position, id) -> {
            // click logic stays same
        });
    }

    // ---------------- UPDATE CALENDAR DATA ----------------
    private void updateCalendar() {

        dayValueData.clear();

        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        for (int i = 0; i < MAX_CALENDAR_COLUMN; i++) {
            EventObjectsSecond obj = new EventObjectsSecond();
            obj.setDate(mCal.getTime());
            obj.setMessage("");
            dayValueData.add(obj);
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        currentDate.setText(formatter.format(cal.getTime()));

        // 🔥 IMPORTANT
        mAdapter.update(
                allEvents,
                "",
                "",
                allEvents,
                listDaysRate,
                dayValueData
        );
    }

    // ---------------- PUBLIC METHODS ----------------

    public void setRates(ArrayList<EventObjectsTime> rates) {
        listDaysRate.clear();
        listDaysRate.addAll(rates);
        updateCalendar();
    }

    public void setSelectedDates(ArrayList<EventObjects> selectedDates) {
        allEvents.clear();
        allEvents.addAll(selectedDates);
        updateCalendar();
    }
}
