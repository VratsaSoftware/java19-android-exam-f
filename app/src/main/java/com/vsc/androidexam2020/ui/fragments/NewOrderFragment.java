package com.vsc.androidexam2020.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import com.vsc.androidexam2020.FileUtils;
import com.vsc.androidexam2020.R;
import com.vsc.androidexam2020.data.local.Order;
import com.vsc.androidexam2020.data.remote.api.ApiWrapper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class NewOrderFragment extends Fragment {

    private static final int RQ_GALLERY = 44;
    private static NewOrderFragment instance;

    private String imageName;
    private long imageSizeInBytes;

    // TODO Ex 3
    private Button btnGetPhoto;
    private Button btnCalc;

    private ProgressBar progressBar;

    private TextView txtFileName;
    private TextView txtFileSize;

    private ImageView imageView;

    private Group grpPart2;

    public static NewOrderFragment newInstance() {
        if(instance == null) instance = new NewOrderFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getViews(view);
        setClickListeners();
    }

    private void getViews(View view) {
        btnGetPhoto = view.findViewById(R.id.btnGetPhoto);
        btnCalc = view.findViewById(R.id.btnCalc);

        progressBar = view.findViewById(R.id.progressBar);

        txtFileName = view.findViewById(R.id.txtFileName);
        txtFileSize = view.findViewById(R.id.txtFileSize);

        imageView = view.findViewById(R.id.imageView);

        grpPart2 = view.findViewById(R.id.grpPart2);
    }

    private void setClickListeners() {
        btnGetPhoto.setOnClickListener(v -> onNewPhotoClicked());
        btnCalc.setOnClickListener(v -> onCalculateOrderClicked());
    }

    private void onNewPhotoClicked() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RQ_GALLERY);
        resetViews();
    }

    private void onCalculateOrderClicked() {
        progressBar.setVisibility(View.VISIBLE);
        btnCalc.setEnabled(false);
        ApiWrapper.getInstance().calculateOrder(imageName, imageSizeInBytes, data -> showEstimatedOrder(data));
    }

    private void showEstimatedOrder(Order order) {
        progressBar.setVisibility(View.GONE);
        btnCalc.setEnabled(true);
        if(order != null) {
            // TODO Ex 7
        } else {
            // TODO Ex 5 show message "Calculation failed!"
        }
    }

    private void resetViews() {
        imageName = "";
        imageSizeInBytes = 0;
        progressBar.setVisibility(View.GONE);
        btnCalc.setEnabled(true);
        grpPart2.setVisibility(View.GONE);
        // TODO Ex 9
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (reqCode == RQ_GALLERY && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                onImageSelected(imageUri, selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // TODO Ex 5 show message "Something went wrong"
            }
        }else {
            // TODO Ex 5 show message "You haven't picked an Image"
        }
    }

    private void onImageSelected(Uri imageUri, Bitmap image) {
        this.imageName = FileUtils.getFileName(imageUri, getActivity());
        this.imageSizeInBytes = Long.parseLong(FileUtils.getFileSize(imageUri, getActivity()));

        imageView.setImageBitmap(image);
        txtFileName.setText(imageName);
        txtFileSize.setText(FileUtils.bytesToKiloBytes(imageSizeInBytes) + " KB");

        grpPart2.setVisibility(View.VISIBLE);
    }

    // TODO Ex 8 use this helper method to show the date properly
    private String calculateDeliveryDate(long completionTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        Date date = new Date(System.currentTimeMillis() + completionTime);
        return sdf.format(date);
    }
}
