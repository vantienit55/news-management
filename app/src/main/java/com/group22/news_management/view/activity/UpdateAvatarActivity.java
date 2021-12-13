package com.group22.news_management.view.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import com.group22.news_management.database.DatabaseHelper;
import com.group22.news_management.presenter.UserPresenter;
import com.group22.news_management.presenter.impl.UserPresenterImpl;
import com.group22.news_management.utils.Session;
import com.group22.newsmanagerment.R;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateAvatarActivity extends AppCompatActivity {

    private ImageView imgCamera, imgFolder, imgUserAvatar;
    private Button btnUpdate, btnCancel;
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher;
    private ActivityResultLauncher<Intent> folderActivityResultLauncher;
    private UserPresenter userPresenter;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avatar);
        initViews();
        imgCameraEventOnClick();
        imgFolderEventOnClick();
        SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        session = new Session(this);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        userPresenter = new UserPresenterImpl(databaseHelper);
        byte[] avatar = userPresenter.findById(session.get().getId()).getAvatar();
        if(avatar != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            imgUserAvatar.setImageBitmap(bitmap);
        }
        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
                        imgUserAvatar.setImageBitmap(bitmap1);
                    }
                });
        folderActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream);
                            imgUserAvatar.setImageBitmap(bitmap2);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
        btnUpdateEventOnClick();
        btnCancelEventOnClick();
    }

    private void initViews() {
        imgCamera = findViewById(R.id.imgCamera);
        imgFolder = findViewById(R.id.imgFolder);
        imgUserAvatar = findViewById(R.id.imgUserAvatar);
        btnUpdate = findViewById(R.id.btnUpdateAvatar);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void imgCameraEventOnClick() {
        imgCamera.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraActivityResultLauncher.launch(intent);
        });
    }

    private void imgFolderEventOnClick() {
        imgFolder.setOnClickListener(view -> {
            Intent intent =  new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            folderActivityResultLauncher.launch(intent);
        });
    }

    private void btnUpdateEventOnClick() {
        btnUpdate.setOnClickListener(view -> {
            long userId = session.get().getId();
            // cast imageView -> bitmap -> byte[]
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgUserAvatar.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] avatar = byteArrayOutputStream.toByteArray();
            userPresenter.updateAvatar(userId, avatar);
        });
    }


    private void btnCancelEventOnClick() {
        btnCancel.setOnClickListener(view -> {

        });
    }
}