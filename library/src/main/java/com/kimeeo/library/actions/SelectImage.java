package com.kimeeo.library.actions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class SelectImage extends BaseAction
{
    public static final int PICK_IMAGE = 1;
    public static final int PICK_CAMERA_IMAGE = 0;

    protected RegisterImageUploadCallBack registerImageUploadCallBack;
    protected ImageView holder;
    protected View triger;
    protected File file;
    protected Uri imageUri;
    protected int maxSize=-1;
    protected boolean isCameraActive;
    protected int selectedMethod;
    private String  title="Select Uploading Method";
    private String photoBaseName="photo";

    public OnResult getOnResult() {
        return onResult;
    }

    public void setOnResult(OnResult onResult) {
        this.onResult = onResult;
    }

    private  OnResult onResult;
    public void setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
    }

    private String folderLocation;


    public SelectImage(Activity activity, RegisterImageUploadCallBack registerImageUploadCallBack)
    {
        super(activity);
        this.registerImageUploadCallBack =registerImageUploadCallBack;
    }
    public SelectImage(Activity activity, RegisterImageUploadCallBack registerImageUploadCallBack, ImageView holder, View triger)
    {
        super(activity);
        this.holder = holder;
        this.triger = triger;
        this.registerImageUploadCallBack =registerImageUploadCallBack;
        if(triger!=null)
            triger.setOnClickListener(tigerHandel);

        if(holder!=null)
            holder.setOnClickListener(tigerHandel);
    }
    public SelectImage(Activity activity, RegisterImageUploadCallBack registerImageUploadCallBack, ImageView holder)
    {
        super(activity);
        this.registerImageUploadCallBack =registerImageUploadCallBack;
        this.holder = holder;
        if(holder!=null)
            holder.setOnClickListener(tigerHandel);
    }
    public void perform()
    {
        openStandardPopup();
    }


    public int getMaxSize() {
        return maxSize;
    }
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getFilePath()
    {
        if(getFile()!=null)
            return getFile().getAbsolutePath();
        return null;
    }
    public File getFile()
    {
        return file;
    }


    View.OnClickListener tigerHandel=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            perform();
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoBaseName() {
        return photoBaseName;
    }

    public void setPhotoBaseName(String photoBaseName) {
        this.photoBaseName = photoBaseName;
    }

    public static class Item{
        public final String text;
        public final int icon;
        public Item(String text, int icon) {
            this.text = text;
            this.icon = icon;
        }
        @Override
        public String toString() {
            return text;
        }
    }


    public void openStandardPopup() {
        final Item[] items = getOptions();
        String[] list = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            list[i] = items[i].text;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getTitle());
        builder.setItems(list, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onOptionSelect(which);

            }
        });
        builder.show();
    }

    public void onOptionSelect(int which) {
        if (which == PICK_CAMERA_IMAGE)
            takeAPicture();
        else if (which == PICK_IMAGE)
            selectAPicture();
    }

    public void selectAPicture() {
        Intent gintent = new Intent();
        gintent.setType("image/*");
        gintent.setAction(Intent.ACTION_GET_CONTENT);
        registerImageUploadCallBack.registerImageUploadCallBack(this);
        activity.startActivityForResult(Intent.createChooser(gintent, "Select Picture"), PICK_IMAGE);
    }

    public void takeAPicture() {
        String fileName = getPhotoBaseName()+new Date().getTime()+".jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");

        File dir= getRootFolder();
        dir = new File(dir,getFolderLocation());
        if(!dir.exists())
            dir.mkdir();
        File target = new File(dir,fileName);

        imageUri = Uri.fromFile(target);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        registerImageUploadCallBack.registerImageUploadCallBack(this);
        activity.startActivityForResult(intent, PICK_CAMERA_IMAGE);
    }

    File rootFolder = Environment.getExternalStorageDirectory();
    public File getRootFolder() {
        return rootFolder;
    }
    public void setRootFolder(File f) {
        rootFolder = f;
    }
    public boolean isValid()
    {
        return file!=null;
    }
    public void imageLoaded(Bitmap bitmap,File incomingFile,int method) {

        File processedFile;
        if(getMaxSize()!=-1)
        {
            if((bitmap.getHeight()>getMaxSize() || bitmap.getWidth()>getMaxSize()))
                processedFile = resizeAndSave(bitmap, incomingFile,getMaxSize());
            else
                processedFile = incomingFile;
        }
        else
            processedFile = incomingFile;
        fileLoaded(processedFile);
        selectedMethod = method;
    }

    private void fileLoaded(File processedFile) {

        this.file = processedFile;

        if(holder!=null)
            Picasso.with(activity).load(file).into(holder);


        if(getOnResult()!=null)
            getOnResult().selected(file);

        if(file!=null &&  file.exists() && file.canRead())
        {
            try {
                MediaStore.Images.Media.insertImage(activity.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            }catch (Exception e)
            {

            }
            if(triger!=null)
                triger.setVisibility(View.GONE);

            if(holder!=null)
                holder.setVisibility(View.VISIBLE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;

        if(resultCode!=Activity.RESULT_OK && getOnResult()!=null)
            getOnResult().fail();

        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK)
                    selectedImageUri = data.getData();
                break;
            case PICK_CAMERA_IMAGE:
                isCameraActive=true;
                if (resultCode == Activity.RESULT_OK)
                    selectedImageUri = imageUri;
                break;
        }


        if (selectedImageUri != null) {
            try {
                String filePath = null;
                String selectedImagePath = getPath(activity, selectedImageUri);
                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                }
                else {
                    String filemanagerstring = selectedImageUri.getPath();
                    filePath = filemanagerstring;
                }

                if (filePath != null) {
                    Bitmap bitmap = decodeFile(filePath);
                    File file = new File(filePath);
                    if(file.exists())
                    {
                        if (bitmap == null)
                            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedImageUri);
                        imageLoaded(bitmap, file,requestCode);
                    }
                }

            } catch (Exception e) {

            }
        }
        registerImageUploadCallBack.unRegisterImageUploadCallBack(this);
        isCameraActive=false;
    }

    public String getFolderLocation() {
        if(folderLocation==null)
            folderLocation = activity.getPackageName();
        return folderLocation;
    }

    public Bitmap decodeFile(String filePath) {

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        Bitmap out = BitmapFactory.decodeFile(filePath,o);
        return out;
    }


    private File resizeAndSave(Bitmap sourceBitmap, File targetFile, int maxSize) {
        File dir = getRootFolder();
        dir = new File(dir, getFolderLocation());
        if (!dir.exists())
            dir.mkdir();

        int originalWidth = sourceBitmap.getWidth();
        int originalHeight = sourceBitmap.getHeight();
        int newWidth = -1;
        int newHeight = -1;
        float multFactor = -1.0F;
        if (originalHeight > originalWidth) {
            newHeight = maxSize;
            multFactor = (float) originalWidth / (float) originalHeight;
            newWidth = (int) (newHeight * multFactor);
        } else if (originalWidth > originalHeight) {
            newWidth = maxSize;
            multFactor = (float) originalHeight / (float) originalWidth;
            newHeight = (int) (newWidth * multFactor);
        } else if (originalHeight == originalWidth) {
            newHeight = maxSize;
            newWidth = maxSize;
        }


        String fileName = getPhotoBaseName() + new Date().getTime() + ".jpg";
        Bitmap resizeBitmaped = Bitmap.createScaledBitmap(sourceBitmap, newWidth, newHeight, false);
        File resizedFile = new File(dir, fileName);
        try {
            if (!resizedFile.exists())
                resizedFile.createNewFile();
        } catch (Exception e)
        {
            System.out.println(e);
        }


        try {
            FileOutputStream fs=new FileOutputStream(resizedFile,false);
            resizeBitmaped.compress(Bitmap.CompressFormat.JPEG, 100, fs);
            fs.flush();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resizeBitmaped.recycle();
        sourceBitmap.recycle();
        if(resizedFile.exists())
        {
            if (isCameraActive)
                targetFile.delete();
            return resizedFile;
        }
        return targetFile;
    }

    public void setOptions(Item[] options) {
        this.options = options;
    }

    Item[] options = {
            new Item("Take a picture", android.R.drawable.ic_menu_camera),
            new Item("Select from gallery", android.R.drawable.ic_menu_gallery)
    };

    public Item[] getOptions() {

        return options;
    }

    public static interface RegisterImageUploadCallBack
    {
        void registerImageUploadCallBack(SelectImage imageSelector);
        void unRegisterImageUploadCallBack(SelectImage imageSelector);
    }

    public static interface OnResult
    {
        void selected(Object file);
        void fail();
    }

    /**
     * Method for return file path of Gallery image
     *
     * @param context
     * @param uri
     * @return path of the selected image file from gallery
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri)
    {

        //check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is <span id="IL_AD8" class="IL_AD">Google Photos</span>.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
