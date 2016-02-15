package com.kimeeo.library.listDataView.dataManagers.deviceDataManager;

import android.content.Context;

import com.kimeeo.library.listDataView.dataManagers.deviceDataManager.BaseDataManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bhavinpadhiyar on 2/15/16.
 */
abstract public class FileDataManager extends BaseDataManager {

    public void garbageCollectorCall() {
        super.garbageCollectorCall();
    }

    public FileDataManager(Context context)
    {
        super(context);
    }

    protected void callService(String url)
    {
        try {
            File file = new File(url);

            if(file.exists() && file.isDirectory()==false) {
                String data = readTxt(file);
                dataHandler(url, data, "DONE");
            }
            else
                dataLoadingDone(null, false);
        }catch(Exception e)
        {
            dataLoadingDone(null, false);
        }
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
