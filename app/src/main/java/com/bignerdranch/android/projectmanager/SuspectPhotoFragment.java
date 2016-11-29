package com.bignerdranch.android.projectmanager;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Eduardo on 10/5/2016.
 */
public class SuspectPhotoFragment extends DialogFragment {
    private static final String EXTRA_PHOTO =
            "com.bignerdranch.android.criminalIntent.photo";

    private static final String ARG_PHOTO = "suspect_image";

    public static SuspectPhotoFragment newInstance(File photoFile) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHOTO, photoFile);

        SuspectPhotoFragment fragment = new SuspectPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        File photoFile = (File) getArguments().getSerializable(ARG_PHOTO);

        Bitmap photo = PictureUtils.getScaledBitmap(photoFile.getPath(), getActivity());

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.suspect_photo_view);
        imageView.setImageBitmap(photo);

        return new AlertDialog.Builder(getActivity()).setView(imageView).create();
    }
}
