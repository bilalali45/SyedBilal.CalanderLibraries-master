package com.syedbilalali.customcalanderview;

import static com.syedbilalali.customcalanderview.CalendarCustomView.langaugeCode;
import static com.syedbilalali.customcalanderview.CalendarCustomView.numberFormat;
import static com.syedbilalali.customcalanderview.CalendarCustomView.selectDate;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.syedbilalali.customcalendarview.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GridAdapterV2 extends ArrayAdapter<EventObjectsSecond> {

    private LayoutInflater inflater;
    private ArrayList<EventObjects> selectedEvents = new ArrayList<>();
    public static ArrayList<EventObjectsSecond> monthlyDates;
    private Calendar currentDate;

    private Date firstDateView, secondDateView;
    private boolean showMiddleRange = false;

    private String firstRate = "";
    private String secondRate = "";

    // ---------------- VIEW HOLDER ----------------
    static class ViewHolder {
        TextView dayText, rateText;
        RelativeLayout wrapper;
        LinearLayout cell;
    }

    // ---------------- CONSTRUCTOR ----------------
    public GridAdapterV2(
            Context context,
            ArrayList<EventObjectsSecond> monthlyDatesDAY,
            Calendar currentDate,
            ArrayList<EventObjects> selectedEvents
    ) {
        super(context, R.layout.single_cell_layout, monthlyDates);
          monthlyDates = monthlyDatesDAY;
        this.currentDate = currentDate;
        this.selectedEvents = selectedEvents;
        this.inflater = LayoutInflater.from(context);
    }

    // ---------------- COUNT (MANDATORY) ----------------
    @Override
    public int getCount() {
        return monthlyDates.size();
    }

    @Override
    public EventObjectsSecond getItem(int position) {
        return monthlyDates.get(position);
    }

    // ---------------- GET VIEW ----------------
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder h;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_cell_layout, parent, false);
            h = new ViewHolder();
            h.dayText = convertView.findViewById(R.id.calendar_date_id);
            h.rateText = convertView.findViewById(R.id.calander_rate);
            h.wrapper = convertView.findViewById(R.id.event_wrapper);
            h.cell = convertView.findViewById(R.id.lir);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        resetCell(h);

        EventObjectsSecond item = getItem(position);
        Calendar cal = Calendar.getInstance();
        cal.setTime(item.getDate());

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        h.dayText.setText(CalendarCustomView.numberFormat.format(day));

        if (!isSelectableDate(day, month, year)) {
            h.dayText.setTextColor(
                    ContextCompat.getColor(getContext(), R.color.lightgrey));
            return convertView;
        }

        if (firstDateView != null && isSameDay(firstDateView, item.getDate())) {
            applyStartDate(h);
        }

        if (secondDateView != null && isSameDay(secondDateView, item.getDate())) {
            applyEndDate(h);
        }

        applyRate(item, h);

        if (showMiddleRange) {
            applyMiddleRange(item, h);
        }

        return convertView;
    }

    // ---------------- RESET CELL ----------------
    private void resetCell(ViewHolder h) {
        h.wrapper.setBackground(null);
        h.cell.setBackgroundColor(Color.WHITE);
        h.dayText.setTextColor(Color.BLACK);
        h.rateText.setVisibility(View.GONE);
    }

    // ---------------- DATE CHECK ----------------
    private boolean isSameDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    private boolean isSelectableDate(int day, int month, int year) {
        Calendar today = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.set(year, month, day);
        return !date.before(today);
    }

    // ---------------- START DATE ----------------
    private void applyStartDate(ViewHolder h) {
        h.wrapper.setBackgroundResource(R.drawable.greencircle);
        h.dayText.setTextColor(Color.WHITE);
    }

    // ---------------- END DATE ----------------
    private void applyEndDate(ViewHolder h) {
        h.wrapper.setBackgroundResource(R.drawable.darkcirlceboder);
        h.dayText.setTextColor(Color.BLACK);
    }

    // ---------------- RATE ----------------
    private void applyRate(EventObjectsSecond item, ViewHolder h) {
        if (item.getMessage() != null && !item.getMessage().isEmpty()) {
            h.rateText.setVisibility(View.VISIBLE);
            h.rateText.setText(
                    CalendarCustomView.numberFormat.format(
                            Double.parseDouble(item.getMessage()))
            );
        }
    }

    // ---------------- MIDDLE RANGE ----------------
    private void applyMiddleRange(EventObjectsSecond item, ViewHolder h) {
        for (EventObjects e : selectedEvents) {
            if (isSameDay(e.getDate(), item.getDate())) {
                h.cell.setBackgroundColor(Color.parseColor("#E6E6E6"));
                break;
            }
        }
    }

    // ---------------- UPDATE DATA ----------------
    public void update(
            ArrayList<EventObjects> selected,
            String firstDate,
            String secondDate,
            ArrayList<EventObjects> events,
            ArrayList<EventObjectsTime> rates,
            ArrayList<EventObjectsSecond> newDates
    ) {

        monthlyDates.clear();
        monthlyDates.addAll(newDates);

        selectedEvents.clear();
        selectedEvents.addAll(selected);

        showMiddleRange = false;

        if (!selectedEvents.isEmpty()) {
            firstDateView = selectedEvents.get(0).getDate();
            secondDateView = selectedEvents.get(selectedEvents.size() - 1).getDate();
        }

        if (selectedEvents.size() > 2) {
            showMiddleRange = true;
            selectedEvents.remove(0);
            selectedEvents.remove(selectedEvents.size() - 1);
        }

        notifyDataSetChanged();
    }
}
