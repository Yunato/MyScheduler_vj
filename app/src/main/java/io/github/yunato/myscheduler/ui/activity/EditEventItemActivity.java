package io.github.yunato.myscheduler.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import io.github.yunato.myscheduler.R;
import io.github.yunato.myscheduler.model.entity.EventItem;
import io.github.yunato.myscheduler.model.repository.EventItemRepository;
import io.github.yunato.myscheduler.ui.fragment.EditEventItemFragment;

public class EditEventItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan_info);

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
            EventItem extraItem = intent.getParcelableExtra(MainDrawerActivity.EXTRA_EVENTITEM);
            EventItem item = extraItem != null ? extraItem : EventItemRepository.createEmpty();
            EditEventItemFragment containerFragment = EditEventItemFragment.newInstance(item);

            Button saveButton = (Button) findViewById(R.id.save_button);
            saveButton.setOnClickListener(containerFragment.getSaveBtnOnClickListener());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, containerFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
