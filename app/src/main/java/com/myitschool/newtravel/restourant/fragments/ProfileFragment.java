package com.myitschool.newtravel.restourant.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myitschool.newtravel.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Models.MyResponse;
import Models.PictureUtils;
import Models.Result;
import Util.APIService;
import Util.Constants;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private static final String RESULT_OK = "result";
    private CircleImageView photo;
    private EditText name;
    private EditText phone;
    private EditText mail;
    private EditText password;
    private EditText pass;
    private EditText pass1;
    private Button but_Ok;
    private SharedPreferences sharedPreferences;
    private Handler mHandler;
    private String Name;
    private String Num;
    private String Mail;
    private String Pass;
    private Button but_cancel;
    private CheckBox Ok;
    private LinearLayout item;
    private Bitmap myBitmap;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private ImageButton photo_add;
    private File mPhotoFile;
    private static final int REQUEST_PHOTO = 2;
    private Uri Uri;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        photo = (CircleImageView) view.findViewById(R.id.photo);
        name = (EditText) view.findViewById(R.id.name_iz);
        phone = (EditText) view.findViewById(R.id.phone_iz);
        mail = (EditText) view.findViewById(R.id.mail_iz);
        password = (EditText) view.findViewById(R.id.password);
        pass = (EditText) view.findViewById(R.id.pass);
        pass1 = (EditText) view.findViewById(R.id.pass1);
        but_Ok = (Button) view.findViewById(R.id.but_Ok);
        Ok = (CheckBox) view.findViewById(R.id.Ok);
        photo_add = (ImageButton) view.findViewById(R.id.photo_add);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        name.setText(sharedPreferences.getString(Constants.USER_NAME, ""));
        phone.setText(sharedPreferences.getString(Constants.USER_NUMBER, ""));
        mail.setText(sharedPreferences.getString(Constants.USER_EMAIL, ""));
        but_cancel = (Button) view.findViewById(R.id.but_Cancel);
        mHandler = new Handler(Looper.getMainLooper());
        item = (LinearLayout) view.findViewById(R.id.item);

//        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(i, 100);
        String path=Constants.apilink+"uploads/"+sharedPreferences.getString(Constants.PHOTO,"");
        Picasso.with(getActivity()).load(path).into(photo);

        String ph = sharedPreferences.getString(Constants.PHOTO, "");
        mPhotoFile = getPhotoFile(ph);
        PackageManager packageManager = getActivity().getPackageManager();
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        photo_add.setEnabled(canTakePhoto);



        photo_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri = FileProvider.getUriForFile(getActivity(),
                        "com.myitschool.newtravel.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName, Uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });
      //  updatePhotoView();

        but_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.cont, new RestaurantFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        Ok.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (Ok.isChecked()) {
                    item.setVisibility(View.VISIBLE);
                } else {
                    item.setVisibility(View.GONE);
                }
            }
        });
        but_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String newname = name.getText().toString();
                final String newphone = phone.getText().toString();
                final String newmial = mail.getText().toString();
                final String newpass = pass.getText().toString();
                final String newpass1 = pass1.getText().toString();
                if (Ok.isChecked()) {
                    String oldPass = password.getText().toString();
                    String uri = Constants.apilink + "users/chek.php?password=" + oldPass + "&id=" + sharedPreferences.getString(Constants.USER_ID, "");

                    OkHttpClient client = new OkHttpClient();
                    //Execute request
                    final Request request = new Request.Builder()
                            .url(uri)
                            .build();
                    //Setup callback
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {

                            final String res = response.body().string();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject ob = new JSONObject(res);
                                        String id = ob.getString("success");
                                        if (id.equals("true")) {
                                            if (newpass.equals(newpass1)) {
                                                String uri = Constants.apilink + "users/update.php?name=" + newname + "&phone=" + newphone +
                                                        "&e-mail=" + newmial + "&password=" + newpass + "&id=" + sharedPreferences.getString(Constants.USER_ID, "");
                                                OkHttpClient client = new OkHttpClient();
                                                //Execute request
                                                final Request request = new Request.Builder()
                                                        .url(uri)
                                                        .build();
                                                //Setup callback
                                                client.newCall(request).enqueue(new Callback() {
                                                    @Override
                                                    public void onFailure(Call call, IOException e) {

                                                    }

                                                    @Override
                                                    public void onResponse(Call call, final Response response) throws IOException {

                                                        final String res = response.body().string();
                                                        mHandler.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    JSONObject ob = new JSONObject(res);
                                                                    String id = ob.getString("success");
                                                                    if (id.equals("true")) {
                                                                        Toast.makeText(getActivity(), "Ваши данные успешно сохранены", Toast.LENGTH_SHORT).show();
                                                                        Uri uri = FileProvider.getUriForFile(getActivity(),
                                                                                "com.myitschool.newtravel.fileprovider",
                                                                                mPhotoFile);
                                                                        uploadFile(uri);
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();

                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(getActivity(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                    }
                                }
                            });
                        }
                    });
                } else {
                    final String uri = Constants.apilink + "users/update_withoutpass.php?name=" + newname + "&phone=" + newphone +
                            "&e-mail=" + newmial + "&id=" + sharedPreferences.getString(Constants.USER_ID, "");
                    OkHttpClient client = new OkHttpClient();
                    //Execute request
                    final Request request = new Request.Builder()
                            .url(uri)
                            .build();
                    //Setup callback
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {

                            final String res = response.body().string();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject ob = new JSONObject(res);
                                        String id = ob.getString("success");
                                        if (id.equals("true")) {
                                            Toast.makeText(getActivity(), "Ваши данные успешно сохранены", Toast.LENGTH_SHORT).show();
                                            uploadFile(Uri);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                    }
                                }
                            });
                        }
                    });
                }
            }

        });

        return view;
    }

    public File getPhotoFile(String name) {
        File externalFilesDir = getActivity().getFilesDir();
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, sharedPreferences.getString(Constants.PHOTO, ""));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            photo.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            photo.setImageBitmap(bitmap);
        }
    }

    private void uploadFile(Uri fileUri) {

        RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), mPhotoFile);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), "My plain");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.apilink)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        APIService api = retrofit.create(APIService.class);
        retrofit2.Call<MyResponse> call = api.uploadImage(requestFile, descBody,Integer.parseInt(sharedPreferences.getString(Constants.USER_ID, "")));
        call.enqueue(new retrofit2.Callback<MyResponse>() {
            @Override
            public void onResponse(retrofit2.Call<MyResponse> call, retrofit2.Response<MyResponse> response) {
                if (!response.body().error) {
                    Toast.makeText(getActivity(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<MyResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
