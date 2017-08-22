package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

public class CourseSearchActivity extends SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "课程搜索");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_course_search);
    }
}
