package com.kimeeo.library.listDataView.dataManagers.deviceDataManager;

import android.Manifest;
import android.content.Context;

import com.gun0912.tedpermission.PermissionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by bhavinpadhiyar on 2/15/16.
 */
abstract public class FileDataManager extends BaseDataManager {

    public FileDataManager(Context context) {
        super(context);
    }

    public void garbageCollectorCall() {
        super.garbageCollectorCall();
    }

    @Override
    public String[] requirePermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    }

    public String[] getFriendlyPermissionsMeaning() {
        return new String[]{"Storage"};
    }

    protected void callService(final String path)
    {
        PermissionListener permissionListener = new PermissionListener()
        {
            @Override
            public void onPermissionGranted() {
                permissionGranted(path);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> arrayList) {
                permissionDenied(arrayList);
                dataHandler(path, null, "ERROR");
            }
        };
        invokePermission(permissionListener);
    }


    protected void permissionDenied(ArrayList<String> arrayList) {

    }

    protected void permissionGranted(String url) {
        File file = new File(url);
        if (file.exists() && file.isDirectory() == false) {
            String data = readTxt(file);
            dataHandler(url, data, "DONE");
        } else
            dataHandler(url, null, "ERROR");
    }


    protected String readTxt(File file){
        StringBuilder text = new StringBuilder();
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {

        }
        return text.toString();
    }

    protected InputStream getInputStream(Context context,String url) throws Exception
    {
        return context.getAssets().open(url);
    }
}
