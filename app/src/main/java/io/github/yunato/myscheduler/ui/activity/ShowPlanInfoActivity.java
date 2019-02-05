package io.github.yunato.myscheduler.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.github.yunato.myscheduler.R;
import io.github.yunato.myscheduler.model.item.EventInfo.EventItem;
import io.github.yunato.myscheduler.ui.fragment.ShowPlanFragment;

public class ShowPlanInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plan_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_outline_close_24px);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            Intent intent = getIntent();
            EventItem item = intent.getParcelableExtra(MainDrawerActivity.EXTRA_EVENTITEM);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ShowPlanFragment.newInstance(item)).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_plan_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_edit) {
            // TODO: 現在表示している予定情報を渡す
            startActivity(new Intent(getApplication(), EditPlanInfoActivity.class));
            overridePendingTransition(0, 0);
        }
        return super.onOptionsItemSelected(item);
    }
}
