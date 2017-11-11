package com.example.dangkhoa.qrcodefirebase.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.dangkhoa.qrcodefirebase.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dangkhoa on 11/9/17.
 */

public class Services {
    public static final int NO_ANIMATION = -1;
    public static final int FROM_RIGHT_TO_LEFT = 0;
    public static final int FROM_LEFT_TO_RIGHT = 1;
    public static final int FROM_BOTTOM_TO_TOP = 2;
    public static final int FROM_TOP_TO_BOTTOM = 3;

    public static void Navigate(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable String tag, Bundle bundle, int direction) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment oldFragment = fragmentManager.findFragmentByTag(fragmentManager.getClass().getName());

        if (oldFragment != null) {
            transaction.remove(oldFragment).commit();
        }

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        switch (direction) {
            case FROM_RIGHT_TO_LEFT: {
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                break;
            }
            case FROM_LEFT_TO_RIGHT: {
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            }
            case FROM_BOTTOM_TO_TOP: {
                transaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top, R.anim.enter_from_top, R.anim.exit_to_bottom);
                break;
            }
            case FROM_TOP_TO_BOTTOM: {
                transaction.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_to_bottom, R.anim.enter_from_bottom, R.anim.exit_to_top);
                break;
            }
        }
        transaction.addToBackStack(tag).replace(R.id.frameLayout, fragment, tag).commit();
    }

    public static void ShowDialog(final Context context, String title, String message, String negativeButton, DialogInterface.OnClickListener negativeButtonOnClickListener, String positiveButton, DialogInterface.OnClickListener positiveButtonOnClickListener ) {
        final AlertDialog.Builder builder = new AlertDialog.Builder((Activity) context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

        if (negativeButton != null) {
            builder.setNegativeButton(negativeButton, negativeButtonOnClickListener);
        }

        if (positiveButton != null) {
            builder.setPositiveButton(positiveButton, positiveButtonOnClickListener);
        }

        final AlertDialog alert = builder.create();
        alert.show();
    }

    public static String GenerateTimestamp() {
        Long timestamp = new Date().getTime() / 1000;
        return String.valueOf(timestamp);
    }

    public static String getTime(String timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return dateFormat.format(new Date(Long.parseLong(timestamp) * 1000L));
    }
}
