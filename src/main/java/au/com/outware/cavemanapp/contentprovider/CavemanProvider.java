package au.com.outware.cavemanapp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import javax.inject.Inject;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.dagger.component.DaggerContentProviderComponent;
import au.com.outware.cavemanapp.dagger.module.ContentProviderModule;
import au.com.outware.cavemanapp.domain.interactor.GetCurrentEnvironment;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class CavemanProvider extends ContentProvider {
    private static final String TYPE = "vnd.android.cursor.item/caveman";
    private static final String COLUMN_CAVEMAN = "caveman";
    private static final String[] COLUMNS_PROJECTION = new String[] {COLUMN_CAVEMAN};

    @Inject
    GetCurrentEnvironment getCurrentEnvironment;

    @Override
    public boolean onCreate() {
        DaggerContentProviderComponent.builder()
                .contentProviderModule(new ContentProviderModule(getContext()))
                .build()
                .inject(this);

        return true;
    }

    @Override
    public Cursor query(@NonNull Uri url, String[] projection, String selection, String[] selectionArgs,
                        String sort) {
        Environment currentEnvironment = getCurrentEnvironment.execute();

        MatrixCursor mc = new MatrixCursor(COLUMNS_PROJECTION);
        mc.addRow(new String[] { new Gson().toJson(currentEnvironment) } );

        return mc;
    }

    @Override
    public String getType(@NonNull Uri url) {
        return TYPE;
    }

    @Override
    public Uri insert(@NonNull Uri url, ContentValues initialValues) {
        throw new SQLException("Unsupported: " + url);
    }

    @Override
    public int delete(@NonNull Uri url, String where, String[] whereArgs) {
        throw new SQLException("Unsupported: " + url);
    }

    @Override
    public int update(@NonNull Uri url, ContentValues values, String where, String[] whereArgs) {
        throw new SQLException("Unsupported: " + url);
    }

}
