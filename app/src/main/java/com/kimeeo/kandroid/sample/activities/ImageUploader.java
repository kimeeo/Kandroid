package com.kimeeo.kandroid.sample.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.androidquery.AQuery;
import com.kimeeo.kandroid.R;
import com.kimeeo.library.actions.LoadDataAQuery;
import com.kimeeo.library.actions.SelectImage;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageUploader extends AppCompatActivity implements SelectImage.RegisterImageUploadCallBack{

    SelectImage si1;
    SelectImage si2;
    SelectImage si3;
    SelectImage si4;
    SelectImage si5;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mage_uploader);


        si1=getSelectImage(R.id.img1);
        si2=getSelectImage(R.id.img2);
        si3=getSelectImage(R.id.img3);
        si4=getSelectImage(R.id.img4);
        si5=getSelectImage(R.id.img5);

        Button upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                upload();
            }
        });
    }

    private SelectImage getSelectImage(int id) {
        ImageView img= (ImageView) findViewById(id);
        SelectImage si=new SelectImage(this,this,img);
        si.setMaxSize(30);
        return  si;

    }

    private void upload() {
        List<File> files = new ArrayList<>();
        addFile(files,si1);
        addFile(files,si2);
        addFile(files,si3);
        addFile(files,si4);
        addFile(files,si5);





        if(files.size()!=0)
        {
            MultipartEntity reqEntity = new MultipartEntity();
            int count =0;
            for (int i = 0; i < files.size(); i++) {
                File file=files.get(i);
                if(file.exists()  && file.canRead()) {
                    reqEntity.addPart("file" + count, new FileBody(file));
                    count +=1;
                }
            }
            try {
                reqEntity.addPart("targetPath", new StringBody("targetPath/01/"));
                reqEntity.addPart("color", new StringBody("#dddddd,#dddd00"));
            }catch (Exception e){}



            Map<String, Object> params = new HashMap<>();
            params.put(AQuery.POST_ENTITY, reqEntity);

            String key="KimeeoApp";
            /*
            key=Base64.encodeToString(key.getBytes(),Base64.DEFAULT);
            key =   key.trim();
            */
            String url ="http://kimeeo.com/rest/fileUpload.php?X-API-KEY="+key;
            LoadDataAQuery.Result result= new LoadDataAQuery.Result()
            {
                @Override
                public void done(String url, Object json, Object status) {
                        System.out.println(json);
                }
            };
            LoadDataAQuery action =new LoadDataAQuery(this);
            action.perform(url,result,params,null);

        }
    }

    private void addFile(List<File> files, SelectImage si) {
        if(si.getFile()!=null)
            files.add(si.getFile());
    }

    SelectImage imageSelector;
    public void registerImageUploadCallBack(SelectImage imageSelector)
    {
        this.imageSelector =imageSelector;
    }
    public void unRegisterImageUploadCallBack(SelectImage imageSelector)
    {
        this.imageSelector = null;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(imageSelector !=null)
            imageSelector.onActivityResult(requestCode,resultCode,data);
    }
}
