package no.ntnu.asd.prosjektfil;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class CameraActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Button button = (Button) findViewById(R.id.takePhotoBtn);
        imageView = (ImageView) findViewById(R.id.photoView);

        //Disable the button if the user has no camera
        if(!hasCamera())
            button.setEnabled(false);
    }

    //Check if the user has a camera
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //Launching the camera
    public void launchCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Take a picture and pass results along to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }



    //If you want to return the image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //Get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            //imageView.setImageBitmap(photo);

            // Convert the Bitmap image to byte array.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            // Convert the byte array to string via Base64.
            String encodedImage = Base64.encodeToString(b , Base64.DEFAULT);
            //System.out.print("Image.toString" + encodedImage);
            System.out.print("String lengde: ");
            System.out.println(encodedImage.length());

            // Convert Base64 string to byte array
            byte[] bb = Base64.decode(encodedImage , Base64.DEFAULT);
            // Convert byte array to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(bb, 0, bb.length);
            imageView.setImageBitmap(bitmap);
        }
    }
}
