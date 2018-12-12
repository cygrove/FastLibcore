package com.cygrove.libcore.updateversion.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.cygrove.libcore.updateversion.CheckUpdateOption;


public abstract class BaseDialogFragment extends DialogFragment {

    protected Context mContext;
    protected Activity mActivity;
    protected CheckUpdateOption mOption;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @NonNull
    @Override
    public abstract Dialog onCreateDialog(Bundle savedInstanceState);

    protected abstract void agreeStoragePermission();

    protected abstract void rejectStoragePermission();


    public void applyOption(CheckUpdateOption option) {
        mOption = option;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!manager.isStateSaved()) {
            super.show(manager, tag);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 0x007) {
                agreeStoragePermission();
            }
        } else {
            rejectStoragePermission();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }
}
