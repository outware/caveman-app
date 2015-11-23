package au.com.outware.cavemanapp.presentation.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import javax.inject.Inject;

import au.com.outware.cavemanapp.R;
import au.com.outware.cavemanapp.application.CavemanApplication;
import au.com.outware.cavemanapp.dagger.component.DaggerActivityComponent;
import au.com.outware.cavemanapp.presentation.adapter.PropertyAdapter;
import au.com.outware.cavemanapp.presentation.viewmodel.EditEnvironmentViewModel;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This activity allows adding properties to a new environment or editing the properties of the
 * selected environment. It dynamically adds views based on the properties in the configuration
 * file. It sets up view, binds data to it. It then grabs the edited data from fields and saves it
 * to the environment.
 *
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
*/
public class EditEnvironmentActivity extends AppCompatActivity {
    public static final String EXTRA_ADD_NEW = "extra_add_new";
    public static final int REQUEST_CODE = 3026;

    @Inject
    EditEnvironmentViewModel viewModel;

    @Bind(R.id.property_list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_environment);
        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                .applicationComponent(CavemanApplication.getApplicationComponent())
                .build()
                .inject(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewModel.setIsNewEnvironment(getIntent().getBooleanExtra(EXTRA_ADD_NEW, false));
        PropertyAdapter adapter = new PropertyAdapter(viewModel.getEnvironment(),
                viewModel.getProperties(), viewModel.isNewEnvironment());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // if adding new environment create a new environment else get current
        // environment
        if (viewModel.isNewEnvironment()) {
            setTitle(R.string.title_add_environment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_save)
    @SuppressWarnings("unused")
    void onSaveClicked() {
        if(viewModel.isNameValid()) {
            viewModel.saveEnvironment();
            setResult(RESULT_OK);
            finish();
        } else {
            TextInputLayout textInputLayout = ButterKnife.findById(recyclerView.getChildAt(0), R.id.name_value_layout);
            textInputLayout.setError("Required");
        }
    }

    @OnClick(R.id.btn_cancel)
    @SuppressWarnings("unused")
    void onCancelClicked() {
        finish();
    }
}
