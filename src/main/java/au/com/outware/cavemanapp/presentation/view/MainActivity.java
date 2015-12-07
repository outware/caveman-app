package au.com.outware.cavemanapp.presentation.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import javax.inject.Inject;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.R;
import au.com.outware.cavemanapp.application.CavemanApplication;
import au.com.outware.cavemanapp.dagger.component.DaggerActivityComponent;
import au.com.outware.cavemanapp.presentation.viewmodel.MainActivityViewModel;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The MainActivity contains the list of all the environments. Allows editing, deleting and adding
 * new environments to the list using the add, edit and delete buttons.
 *
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
*/
public class MainActivity extends AppCompatActivity implements OnItemClickListener,
        DialogInterface.OnClickListener {

    @Inject
    MainActivityViewModel viewModel;

    private ArrayAdapter<Environment> adapter;
    @Nullable
    private Dialog dialog;

    @Bind(R.id.list)
    ListView listView;
    @Bind(R.id.btn_edit)
    Button editButton;
    @Bind(R.id.btn_delete)
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                .applicationComponent(CavemanApplication.getApplicationComponent())
                .build()
                .inject(this);

        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Array Adapter for the list of environments
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice,
                viewModel.getEnvironments());

        listView.setAdapter(adapter);
        configureList();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EditEnvironmentActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.clear();
            adapter.addAll(viewModel.getEnvironments());
            configureList();
        }
    }

    /**
     * Save the current environment every time the selection is changed
     */
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
        viewModel.setCurrentEnvironment(this.adapter.getItem(pos));
        enableButtons(viewModel.shouldEnableButtons());
    }

    /**
     * On confirmation delete the environment and save the current environment to production.
     * Initialize the screen
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            // delete the environment on confirmation from dialog
            adapter.remove(viewModel.getCurrentEnvironment());
            viewModel.removeCurrentEnvironment();
            configureList();
        }
        dialog.dismiss();
        this.dialog = null;
    }

    @OnClick(R.id.btn_add)
    @SuppressWarnings("unused")
    void onAddClick() {
        Intent intent = new Intent(this, EditEnvironmentActivity.class);
        intent.putExtra(EditEnvironmentActivity.EXTRA_ADD_NEW, true);
        startActivityForResult(intent, EditEnvironmentActivity.REQUEST_CODE);
    }

    @OnClick(R.id.btn_edit)
    @SuppressWarnings("unused")
    void onEditClick() {
        startActivity(new Intent(this, EditEnvironmentActivity.class));
    }

    @OnClick(R.id.btn_delete)
    @SuppressWarnings("unused")
    void onDeleteClick() {
        dialog = new AlertDialog.Builder(this).setTitle(R.string.title_delete)
                .setMessage(R.string.delete_message)
                .setPositiveButton(R.string.delete_positive, this)
                .setNegativeButton(R.string.cancel, this).show();
    }

    private void configureList() {
        enableButtons(viewModel.shouldEnableButtons());
        listView.setSelection(viewModel.getSelectedItemPosition());
        listView.setItemChecked(viewModel.getSelectedItemPosition(), true);
    }

    /**
     * Enables the buttons if it is not in production
     *
     * @param enable
     */
    private void enableButtons(boolean enable) {
        editButton.setEnabled(enable);
        deleteButton.setEnabled(enable);
    }
}
