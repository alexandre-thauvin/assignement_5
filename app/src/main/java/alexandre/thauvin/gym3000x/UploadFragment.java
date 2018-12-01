package alexandre.thauvin.gym3000x;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class UploadFragment extends Fragment implements View.OnClickListener{
    MainActivity activity;
    private static final int    READ_REQUEST_CODE = 42;
    private StorageReference mStorageRef;
    ImageView uploadFile;
    private ImageView           thumbnail;
    ImageView           downloadFile;
    Uri                         fileURI;
    String              fileName;


    public UploadFragment() {
    }

    public static UploadFragment newInstance() {
        UploadFragment fragment = new UploadFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.upload_fragment, container, false);
        activity = (MainActivity) getActivity();

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
        }

        v.findViewById(R.id.uploadfile).setOnClickListener(this);
        v.findViewById(R.id.thumbnail).setOnClickListener(this);
        v.findViewById(R.id.downloadfile).setOnClickListener(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        uploadFile = v.findViewById(R.id.uploadfile);
        downloadFile = v.findViewById(R.id.downloadfile);
        thumbnail = v.findViewById(R.id.thumbnail);
        return v;
    }

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void uploadFile() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            if (data != null) {
                fileURI = data.getData();

                    storeFileInFirebase(fileURI);
                    //updateUI("thumbnail");
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.uploadfile:
                uploadFile();
                break;
            case R.id.downloadfile:
                downloadFile();
        }
    }


    private void storeFileInFirebase(Uri uri) {
        StorageReference destinationRef;
        Uri file;

        file = uri;

        fileName = uri.getPath().substring(uri.getPath().lastIndexOf("/")+1) + ".pdf";
        destinationRef = mStorageRef.child(fileName);
        destinationRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(activity,"Upload Finished", Snackbar.LENGTH_LONG).show();
                        // Get a URL to the uploaded content
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(activity,"Upload Failed", Snackbar.LENGTH_LONG).show();
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void downloadFile(){

        StorageReference islandRef = mStorageRef.child(fileName);

//        try{
//            final File localFile = File.createTempFile("download", "pdf");
//            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(activity, localFile.getAbsolutePath(), Snackbar.LENGTH_LONG).show();
//                    final Uri data = FileProvider.getUriForFile(activity, "myprovider", localFile);
//                    activity.grantUriPermission(activity.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    final Intent intent = new Intent(Intent.ACTION_VIEW)
//                            .setDataAndType(data, "*/*")
//                            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    activity.startActivity(intent);
//
//                    // Local temp file has been created
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    Toast.makeText(activity,"Download Failed", Snackbar.LENGTH_LONG).show();
//
//                    // Handle any errors
//                }
//            });
//        }
//
//        catch (java.io.IOException e){
//            Log.d("download", "downloadFile: " + e.getCause());
//        }

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
             Toast.makeText(activity, "Download finished", Snackbar.LENGTH_LONG).show();
             try {

                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)));
                 try {
                     bos.write(bytes);
                     bos.flush();
                     bos.close();

                     final Uri data = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + " " + fileName));
                    activity.grantUriPermission(activity.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                     String mimetype = "application/pdf";
                     Intent myIntent = new Intent(Intent.ACTION_VIEW);
                     myIntent.setDataAndType(data, mimetype);
                    myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                     Intent intentChooser = Intent.createChooser(myIntent, "Choose Pdf Application");
                     activity.startActivity(intentChooser);
                 }
                 catch (java.io.IOException e){
                     e.getMessage();
                 }
             }
             catch (FileNotFoundException e){
                 Log.d("File", "onSuccess: "+ e.getMessage());
             }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(activity, "Download failed", Snackbar.LENGTH_LONG).show();

                // Handle any errors
            }
        });



    }

    /*public void disableThumbnail()
    {
        updateUI("menu");
    }*/

   /* private void updateUI(String state)
    {
        if (state.equals("menu"))
        {
            thumbnail.setVisibility(GONE);
            downloadFile.setVisibility(VISIBLE);
            uploadFile.setVisibility(VISIBLE);
            //storeFileInFirebase(mCurrentPhotoPath);
        }
        else
        {
            thumbnail.setVisibility(VISIBLE);
            downloadFile.setVisibility(GONE);
            uploadFile.setVisibility(GONE);
        }

    }*/
}
