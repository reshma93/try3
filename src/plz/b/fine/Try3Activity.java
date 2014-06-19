package plz.b.fine;

import android.app.Activity;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
 
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import android.os.Bundle;

public class Try3Activity extends Activity {
    /** Called when the activity is first created. */
	 private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	    public static final int MEDIA_TYPE_IMAGE = 1;
	    public static final int MEDIA_TYPE_VIDEO = 2;
	    
	    // directory name to store captured images and videos
	    private static final String IMAGE_DIRECTORY_NAME = "test3";
	 
	    private Uri fileUri; // file url to store image/video
	    private int i=0;
	    private Button btnCapturePicture,btnCapturePicture2,btnCapturePicture3,done_obj;
	    private String []s=new String[3];    //declare an array for storing the files i.e the path of your source files
        
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        s[0]=null;
	        s[1]=null;
	        s[2]=null;
	        btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
	        btnCapturePicture2 = (Button) findViewById(R.id.btnCapturePicture2);
	        btnCapturePicture3 = (Button) findViewById(R.id.btnCapturePicture3);
	        done_obj=(Button)findViewById(R.id.done);
	 
	        /**
	         * Capture image button click event
	         */
	        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View v) {
	                // capture picture
	                captureImage();
	                String temp2=fileUri.toString();
	                temp2=temp2.substring(7);
	               
	                s[0]=temp2;
	                if(i==0)
	                i++;
	            }
	        });
	        
	        btnCapturePicture2.setOnClickListener(new View.OnClickListener() {
	       	 
	            public void onClick(View v) {
	                // capture picture
	                captureImage();
	                String temp2=fileUri.toString();
	                temp2=temp2.substring(7);
	               
	                s[1]=temp2;
	                if(i==1)
	                i++;
	            }
	        });
	        
	        btnCapturePicture3.setOnClickListener(new View.OnClickListener() {
	       	 
	            public void onClick(View v) {
	                // capture picture
	                captureImage();
	                String temp2=fileUri.toString();
	                temp2=temp2.substring(7);
	               
	                s[2]=temp2;
	                if(i==2)
	                i++;
	            }
	        });
	        
	        done_obj.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				        check();

					
				}
			});
	 
	        /**
	         * Record video button click event
	         */
	 
	        // Checking camera availability
	        if (!isDeviceSupportCamera()) {
	            Toast.makeText(getApplicationContext(),
	                    "Sorry! Your device doesn't support camera",
	                    Toast.LENGTH_LONG).show();
	            // will close the app if the device does't have camera
	            finish();
	        }
	    }
	 
	    /**
	     * Checking device has camera hardware or not
	     * */
	    private void check(){
	    	String temp=Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+ IMAGE_DIRECTORY_NAME+"/file.zip";
	    	Toast.makeText(this, temp,Toast.LENGTH_SHORT ).show();
            
			Compress c =new Compress(s,temp,i);   //first parameter is d files second parameter is zip file name
		    c.zip();    //call the zip function
	    	Toast.makeText(this, "compressed",Toast.LENGTH_SHORT ).show();
	    }
	    
	    private boolean isDeviceSupportCamera() {
	        if (getApplicationContext().getPackageManager().hasSystemFeature(
	                PackageManager.FEATURE_CAMERA)) {
	            // this device has a camera
	            return true;
	        } else {
	            // no camera on this device
	            return false;
	        }
	    }
	 
	    /**
	     * Capturing Camera Image will lauch camera app requrest image capture
	     */
	    private void captureImage() {
	        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 
	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	 
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	 
	        // start the image capture Intent
	        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

	    }
	 
	    /**
	     * Here we store the file url as it will be null after returning from camera
	     * app
	     */
	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	 
	        // save file url in bundle as it will be null on scren orientation
	        // changes
	        outState.putParcelable("file_uri", fileUri);
	    }
	 
	    @Override
	    protected void onRestoreInstanceState(Bundle savedInstanceState) {
	        super.onRestoreInstanceState(savedInstanceState);
	 
	        // get the file url
	        fileUri = savedInstanceState.getParcelable("file_uri");
	    }
	 
	    /**
	     * Recording video
	     */
	   
	    /**
	     * Receiving activity result method will be called after closing the camera
	     * */
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        // if the result is capturing Image
	        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	                Toast.makeText(this, "Image saved" , Toast.LENGTH_LONG).show();

	                // successfully captured the image
	                // display it in image view
	               
	            } else if (resultCode == RESULT_CANCELED) {
	                // user cancelled Image capture
	                Toast.makeText(getApplicationContext(),
	                        "User cancelled image capture", Toast.LENGTH_SHORT)
	                        .show();
	            } else {
	                // failed to capture image
	                Toast.makeText(getApplicationContext(),
	                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        }
	    }
	 
	    /**
	     * Display image from a path to ImageView
	     */
	   
	     
	    /**
	     * ------------ Helper Methods ---------------------- 
	     * */
	 
	    /**
	     * Creating file uri to store image/video
	     */
	    public Uri getOutputMediaFileUri(int type) {
	        return Uri.fromFile(getOutputMediaFile(type));
	    }
	 
	    /**
	     * returning image / video
	     */
	    private static File getOutputMediaFile(int type) {
	 
	        // External sdcard location
	        File mediaStorageDir = new File(
	                Environment
	                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
	                IMAGE_DIRECTORY_NAME);
	 
	        // Create the storage directory if it does not exist
	        if (!mediaStorageDir.exists()) {
	            if (!mediaStorageDir.mkdirs()) {
	                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
	                        + IMAGE_DIRECTORY_NAME + " directory");
	                return null;
	            }
	        }
	 
	        // Create a media file name
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	                Locale.getDefault()).format(new Date());
	        File mediaFile;
	        if (type == MEDIA_TYPE_IMAGE) {
	            mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                    + "IMG_" + timeStamp + ".jpg");
	        } else if (type == MEDIA_TYPE_VIDEO) {
	            mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                    + "VID_" + timeStamp + ".mp4");
	        } else {
	            return null;
	        }
	 
	        return mediaFile;
	    }
	}
