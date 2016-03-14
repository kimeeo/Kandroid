package com.kimeeo.library.actions;

import android.app.Activity;
import android.support.v4.util.Pair;
import android.view.View;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 2/2/16.
 */
public class Action extends BaseAction{

    public static String ACTION_PHONE="phone";
    public static String ACTION_OPEN_APP="openApp";
    public static String ACTION_SMS="sms";
    public static String ACTION_SMS_DIRECT="smsDirect";
    public static String ACTION_OPEN_URL="openURL";
    public static String ACTION_OPEN_URL_IN_APP="openURLInApp";
    public static String ACTION_OPEN_URL_CHROME="openURLInChroame";
    public static String ACTION_MAIL_TO="mailto";

    public static String ACTION_SHOW_BUSY_DIALOG="showBusyDialog";
    public static String ACTION_HIDE_BUSY_DIALOG="hideBusy";
    public static String ACTION_SHOW_MSG="showMsg";
    public static String ACTION_TEXT_SHARE="textShare";
    public static String ACTION_NAVIGATE_TO="navigaeTo";
    public static String ACTION_DOWNLOAD_FILE="downloadFile";
    public static String ACTION_DOWNLOAD_FILE_AND_OPEN="downloadFileAndOpen";
    public static String ACTION_IMG_SHARE="imgShare";
    public static String ACTION_IMG_SET="imgSet";
    public static String ACTION_COPY="copy";



    public static String ATTRIBUTE_URL="url";
    public static String ATTRIBUTE_TITLE="title";
    public static String ATTRIBUTE_DATA="data";

    public static String ATTRIBUTE_SUB_TITLE="subTitle";
    public static String ATTRIBUTE_NO="no";
    public static String ATTRIBUTE_BODY="body";
    public static String ATTRIBUTE_CLASS_PATH="classPath";
    public static String ATTRIBUTE_SHOW_FAIL="showFailMsg";
    public static String ATTRIBUTE_CONFIRM="confirm";

    public static String ATTRIBUTE_APP_NAME="appName";
    public static String ATTRIBUTE_MSG="msg";
    public static String ATTRIBUTE_DURATION="duration";


    public static String ATTRIBUTE_SHOW_PROGRESS="showProgress";
    public static String ATTRIBUTE_LONG="long";
    public static String ATTRIBUTE_SUCCESS_MSG="success";
    public static String ATTRIBUTE_FAIL_MSG="fail";

    public static String ATTRIBUTE_LATITUDE="latitude";
    public static String ATTRIBUTE_LONGITUDE="longitude";
    public static String ATTRIBUTE_ADDRESS="address";

    OpenCharomeTab openCharomeTab;
    ShowBusyDialog showBusyDialog;
    SMS sms;
    OpenBrowser openBrowser;
    OpenApp openApp;
    OpenInAppBrowser openInAppBrowser;
    Toast toast;
    TextShare textShare;
    DownloadAndOpen downloadAndOpen;
    ImageSet imageSet;
    ImageShare imageShare;
    Download download;
    Copy copy;
    NavigateTo navigateTo;
    LaunchActivity launchActivity;
    MailTo mailTo;
    Phone phone;

    public Action(Activity activity) {
        super(activity);
    }

    public OpenCharomeTab getOpenCharomeTab() {
        if(openCharomeTab==null)
            openCharomeTab = new OpenCharomeTab(activity);
        return openCharomeTab;
    }

    public ShowBusyDialog getShowBusyDialog() {
        if(showBusyDialog==null)
            showBusyDialog = new ShowBusyDialog(activity);
        return showBusyDialog;
    }

    public SMS getSms() {
        if(sms==null)
            sms = new SMS(activity);
        return sms;
    }

    public OpenBrowser getOpenBrowser() {
        if(openBrowser==null)
            openBrowser = new OpenBrowser(activity);
        return openBrowser;
    }

    public OpenApp getOpenApp() {
        if(openApp==null)
            openApp = new OpenApp(activity);
        return openApp;
    }

    public OpenInAppBrowser getOpenInAppBrowser() {
        if(openInAppBrowser==null)
            openInAppBrowser = new OpenInAppBrowser(activity);
        return openInAppBrowser;
    }

    public Toast getToast() {
        if(toast==null)
            toast = new Toast(activity);
        return toast;
    }

    public TextShare getTextShare() {
        if(textShare==null)
            textShare = new TextShare(activity);
        return textShare;
    }

    public DownloadAndOpen getDownloadAndOpen() {
        if(downloadAndOpen==null)
            downloadAndOpen = new DownloadAndOpen(activity);
        return downloadAndOpen;
    }

    public ImageSet getImageSet() {
        if(imageSet==null)
            imageSet = new ImageSet(activity);
        return imageSet;
    }

    public ImageShare getImageShare() {
        if(imageShare==null)
            imageShare = new ImageShare(activity);
        return imageShare;
    }

    public Download getDownload() {
        if(download==null)
            download= new Download(activity);
        return download;
    }

    public Copy getCopy() {
        if(copy==null)
            copy= new Copy(activity);
        return copy;
    }

    public NavigateTo getNavigateTo() {
        if(navigateTo==null)
            navigateTo= new NavigateTo(activity);
        return navigateTo;
    }

    public LaunchActivity getLaunchActivity() {
        if(launchActivity==null)
            launchActivity= new LaunchActivity(activity);
        return launchActivity;
    }

    public MailTo getMailTo() {
        if(mailTo==null)
            mailTo= new MailTo(activity);
        return mailTo;
    }

    public Phone getPhone() {
        if(phone==null)
            phone= new Phone(activity);
        return phone;
    }

    public void clear()
    {
        super.clear();
        if(openCharomeTab!=null)
            showBusyDialog.clear();
        showBusyDialog=null;
        if(openCharomeTab!=null)
            openCharomeTab.clear();
        openCharomeTab=null;
        if(sms!=null)
            sms.clear();
        sms=null;
        if(openBrowser!=null)
            openBrowser.clear();
        openBrowser=null;

        if(openInAppBrowser!=null)
            openInAppBrowser.clear();
        openInAppBrowser=null;

        if(toast!=null)
            toast.clear();
        toast=null;

        if(textShare!=null)
            textShare.clear();
        textShare=null;

        if(downloadAndOpen!=null)
            downloadAndOpen.clear();
        downloadAndOpen=null;


        if(imageSet!=null)
            imageSet.clear();
        imageSet=null;

        if(imageShare!=null)
            imageShare.clear();
        imageShare=null;

        if(download!=null)
            download.clear();
        download=null;

        if(copy!=null)
            copy.clear();
        copy=null;

        if(navigateTo!=null)
            navigateTo.clear();
        navigateTo=null;

        if(launchActivity!=null)
            launchActivity.clear();
        launchActivity=null;
        if(mailTo!=null)
            mailTo.clear();
        mailTo=null;
        if(phone!=null)
            phone.clear();
        phone=null;


    }

    public void openChromeTab(String url)
    {
        OpenCharomeTab openCharomeTab = getOpenCharomeTab();
        openCharomeTab.perform(url);
    }




    public void mailTo(String url) {
        MailTo mailTo=getMailTo();
        mailTo.perform(url);
    }
    public void phone(String phoneNo)
    {
        Phone phone=getPhone();
        phone.perform(phoneNo);
    }
    public void sendSMSDirect(final String phoneNo,final String body,Boolean confirm)
    {

        SMS sms =getSms();
        sms.perform(phoneNo, body, confirm);
    }
    public void sendSMS(String phoneNo,String body)
    {
        SMS sms =getSms();
        sms.perform(phoneNo, body);
    }
    public void openURL(String link) {
        OpenBrowser openBrowser =getOpenBrowser();
        openBrowser.perform(link);
    }
    public void openApp(String classPath, boolean showFailMsg, String appName)
    {
        OpenApp openApp = getOpenApp();
        openApp.perform(classPath, showFailMsg, appName);
    }
    public void openURLInAPP(String link, String title, String subTitle,Class webActivity)
    {
        OpenInAppBrowser openInAppBrowser=getOpenInAppBrowser();
        openInAppBrowser.perform(link, title, subTitle, webActivity);
    }
    public void openURLInAPP(String link, String title, String subTitle) {
        OpenInAppBrowser openInAppBrowser=getOpenInAppBrowser();
        openInAppBrowser.perform(link, title, subTitle);
    }
    public void openURLInAPP(String link, String title) {
        OpenInAppBrowser openInAppBrowser=getOpenInAppBrowser();
        openInAppBrowser.perform(link, title);
    }
    public void openURLInAPP(String link) {
        OpenInAppBrowser openInAppBrowser=getOpenInAppBrowser();
        openInAppBrowser.perform(link);
    }

    public void showBusy(String msg, Integer duration) {
        hideBusy();
        ShowBusyDialog showBusyDialog =getShowBusyDialog();
        showBusyDialog.perform(msg, duration);
    }
    public void showBusy(String msg) {
        hideBusy();
        ShowBusyDialog showBusyDialog =getShowBusyDialog();
        showBusyDialog.perform(msg, -1);
    }

    public void showBusy(String msg,String message, Integer duration) {
        hideBusy();
        ShowBusyDialog showBusyDialog =getShowBusyDialog();
        showBusyDialog.perform(msg, message, duration);
    }

    public void hideBusy() {
        ShowBusyDialog showBusyDialog =getShowBusyDialog();
        if(showBusyDialog !=null)
            showBusyDialog.hideBusy();
    }
    public void showMSG(String msg)
    {
        Toast toast = getToast();
        toast.perform(msg);
    }
    public void showMSG(String msg,boolean isLong) {
        Toast toast = getToast();
        toast.perform(msg, isLong);
    }
    public void textShare(String data, String title) {
        TextShare textShare=getTextShare();
        textShare.perform(data, title);
    }


    public void downloadAndOpen(String link, String location, final String shareTitle, boolean showProgress, final String success, final String fail, final Download.DownloadResult downloadResult) {
        DownloadAndOpen downloadAndOpen=getDownloadAndOpen();
        downloadAndOpen.perform(link, location, shareTitle, showProgress, success, fail, downloadResult);
    }
    public void setImage(String link,String location,String shareTitle, boolean aTrue, final String success,final String fail,final Download.DownloadResult downloadResult) {
        ImageSet imageSet = getImageSet();
        imageSet.perform(link, location, shareTitle, aTrue, success, fail, downloadResult);
    }
    public void shareImage(String link,String location,String shareTitle, boolean aTrue, final String success,final String fail,final Download.DownloadResult downloadResult) {
        ImageShare imageShare=getImageShare();
        imageShare.perform(link, location, shareTitle, aTrue, success, fail, downloadResult);
    }


    public void download(String link,String location,File targetFolder,final Boolean showProgress,final Download.DownloadResult downloadResult,String fileName)
    {
        Download download=getDownload();
        download.perform(link, location,targetFolder, showProgress, downloadResult, fileName);
    }
    public void download(String link,String location,final Boolean showProgress,final Download.DownloadResult downloadResult,String fileName)
    {
        Download download=getDownload();
        download.perform(link, location, showProgress, downloadResult, fileName);
    }
    public void download(String link,String location,final Boolean showProgress,final Download.DownloadResult downloadResult)
    {
        Download download=getDownload();
        download.perform(link, location, showProgress, downloadResult);
    }
    public void download(String link,String location, boolean showProgress,final String success,final String fail,final Download.DownloadResult downloadResult) {
        Download download=getDownload();
        download.perform(link, location, showProgress, success, fail, downloadResult);
    }

    public void copy(String data, String sucess)
    {
        Copy copy=getCopy();
        copy.perform(data, sucess);
    }
    public void copy(String data)
    {
        Copy copy=getCopy();
        copy.perform(data);
    }
    public void navigateTo(long latitude,long longitude,String address) {
        NavigateTo navigateTo=getNavigateTo();
        navigateTo.perform(latitude, longitude, address);
    }
    public void navigateTo(String latitude,String longitude,String address)
    {
        NavigateTo navigateTo=getNavigateTo();
        navigateTo.perform(latitude, longitude, address);
    }
    public void launchActivity(Class target,boolean killCurrunt) {
        LaunchActivity launchActivity=getLaunchActivity();
        launchActivity.perform(target, killCurrunt);
    }
    public void launchActivity(Class target) {
        LaunchActivity launchActivity=getLaunchActivity();
        launchActivity.perform(target);
    }
    public void launchActivity(Class target,Pair<View, String>[] sharedElements,Map<String,Object> data,boolean killCurrunt) {
        LaunchActivity launchActivity=getLaunchActivity();
        launchActivity.perform(target, sharedElements, data, killCurrunt);
    }
    public void launchActivity(Activity sourceActivity,Class target,Pair<View, String>[] sharedElements,Map<String,Object> data,boolean killCurrunt) {
        LaunchActivity launchActivity=getLaunchActivity();
        launchActivity.perform(sourceActivity, target, sharedElements, data, killCurrunt);
    }
    public void perform(String action)
    {
        String actionType = action.substring(0, action.indexOf("?"));
        actionType.toLowerCase();
        String valuesRaw=action.substring(action.indexOf("?")+1,action.length());
        String[] values = action.substring(action.indexOf("?")+1,action.length()).split("&");
        String key;
        String varValue;
        Map<String,String> actionMap= new HashMap<>();
        for (String entry:values) {
            String[] splitVal=entry.split("=");
            key=splitVal[0];
            varValue=splitVal[1];
            actionMap.put(key,varValue);
        }

        //val="openURLInApp?url=http://www.google.com&title=ABC&subTitle=dffs";
        //val="openURLInApp?url=http://www.google.com&title=ABC";
        //val="openURLInApp?url=http://www.google.com";


        //val="phone?no=8469492621";

        //val="sms?no=8469492621&body=FREE GIFT CLAIM";
        //val="sms?no=8469492621";

        //val="openURL?url=http://www.google.com";

        //val="mailto?apps@edspl.com?subject=App Feedback";



        if(actionType.equals(ACTION_PHONE.toLowerCase()))
        {
            //DONE
            phone(actionMap.get(ATTRIBUTE_NO));
        }
        else if(actionType.equals(ACTION_SMS_DIRECT.toLowerCase()))
        {
            //DONE
            final String no =actionMap.get(ATTRIBUTE_NO);
            final String body =actionMap.get(ATTRIBUTE_BODY);
            String confirm=actionMap.get(ATTRIBUTE_CONFIRM);
            if(confirm==null)
                confirm="true";

            if(confirm.equals("true"))
                sendSMSDirect(no, body, true);
            else
                sendSMSDirect(no, body, false);

        }
        else if(actionType.equals(ACTION_SMS.toLowerCase()))
        {
            //DONE
            String no =actionMap.get(ATTRIBUTE_NO);
            String body =actionMap.get(ATTRIBUTE_BODY);
            sendSMS(no,body);
        }
        else if(actionType.equals(ACTION_OPEN_URL.toLowerCase()))
        {
            //DONE
            String link = actionMap.get(ATTRIBUTE_URL);
            if(link.startsWith("http")==false)
                link ="http://"+link;
            openURL(link);
        }
        else if(actionType.equals(ACTION_MAIL_TO.toLowerCase()))
        {
            //DONE
            mailTo(valuesRaw);
        }
        else if(actionType.equals(ACTION_OPEN_APP.toLowerCase()))
        {
            //DONE
            String classPath=actionMap.get(ATTRIBUTE_CLASS_PATH);
            String showFailMsg=actionMap.get(ATTRIBUTE_SHOW_FAIL);
            if(showFailMsg==null)
                showFailMsg="false";

            String appName=actionMap.get(ATTRIBUTE_APP_NAME);
            if(appName==null)
                appName="Download APP";

            openApp(classPath,showFailMsg.equals("true"),appName);
        }
        else if(actionType.equals(ACTION_SHOW_MSG.toLowerCase()))
        {
            //DONE
            String msg=actionMap.get(ATTRIBUTE_MSG);
            String longDuration = actionMap.get(ATTRIBUTE_LONG);
            if(longDuration==null)
                longDuration="true";
            showMSG(msg, longDuration.equals("true"));
        }
        else if(actionType.equals(ACTION_TEXT_SHARE.toLowerCase()))
        {
            //DONE
            String title = actionMap.get(ATTRIBUTE_TITLE);
            String data = actionMap.get(ATTRIBUTE_DATA);
            textShare(data, title);
        }
        else if(action.equals(ACTION_COPY))
        {
            //DONE
            final String success = actionMap.get(ATTRIBUTE_SUCCESS_MSG);
            String data = actionMap.get(ATTRIBUTE_DATA);
            copy(data, success);
        }
        else if(action.equals(ACTION_NAVIGATE_TO))
        {
            //DONE
            String latitude= actionMap.get(ATTRIBUTE_LATITUDE);
            String longitude= actionMap.get(ATTRIBUTE_LONGITUDE);
            String address= actionMap.get(ATTRIBUTE_ADDRESS);
            navigateTo(latitude, longitude, address);
        }
        else if(action.equals(ACTION_DOWNLOAD_FILE.toLowerCase()))
        {
            //DONE
            String link = actionMap.get(ATTRIBUTE_URL);
            if(link.startsWith("http")==false)
                link ="http://"+link;
            String title = actionMap.get(ATTRIBUTE_TITLE);
            String showProgress = actionMap.get(ATTRIBUTE_SHOW_PROGRESS);
            if(showProgress==null)
                showProgress="true";
            final String success = actionMap.get(ATTRIBUTE_SUCCESS_MSG);
            final String fail = actionMap.get(ATTRIBUTE_FAIL_MSG);
            download(link, null, showProgress.equals("true"), success, fail, null);
        }
        else if(action.equals(ACTION_IMG_SHARE))
        {
            //DONE
            String link = actionMap.get(ATTRIBUTE_URL);
            if(link.startsWith("http")==false)
                link ="http://"+link;
            final String title = actionMap.get(ATTRIBUTE_TITLE);
            String showProgress = actionMap.get(ATTRIBUTE_SHOW_PROGRESS);
            if(showProgress==null)
                showProgress="true";
            final String success = actionMap.get(ATTRIBUTE_SUCCESS_MSG);
            final String fail = actionMap.get(ATTRIBUTE_FAIL_MSG);

            shareImage(link, null, title, showProgress.equals("true"), success, fail, null);
        }
        else if(action.equals(ACTION_IMG_SET))
        {
            //DONE
            String link = actionMap.get(ATTRIBUTE_URL);
            if(link.startsWith("http")==false)
                link ="http://"+link;
            final String title = actionMap.get(ATTRIBUTE_TITLE);
            String showProgress = actionMap.get(ATTRIBUTE_SHOW_PROGRESS);
            if(showProgress==null)
                showProgress="true";
            final String success = actionMap.get(ATTRIBUTE_SUCCESS_MSG);
            final String fail = actionMap.get(ATTRIBUTE_FAIL_MSG);

            setImage(link, null, title, showProgress.equals("true"), success, fail, null);
        }
        else if(action.equals(ACTION_DOWNLOAD_FILE_AND_OPEN))
        {
            //DONE
            String link = actionMap.get(ATTRIBUTE_URL);
            if(link.startsWith("http")==false)
                link ="http://"+link;


            final String title = actionMap.get(ATTRIBUTE_TITLE);
            String showProgress = actionMap.get(ATTRIBUTE_SHOW_PROGRESS);
            if(showProgress==null)
                showProgress="true";
            final String success = actionMap.get(ATTRIBUTE_SUCCESS_MSG);
            final String fail = actionMap.get(ATTRIBUTE_FAIL_MSG);
            downloadAndOpen(link, null, title, showProgress.equals("true"), success, fail, null);
        }
        else if(actionType.equals(ACTION_SHOW_BUSY_DIALOG.toLowerCase()))
        {
            //DONE
            String msg=actionMap.get(ATTRIBUTE_MSG);
            if(msg==null)
                msg = "";
            Integer duration=Integer.parseInt(actionMap.get(ATTRIBUTE_DURATION));
            if(duration==null)
                duration=-1;
            showBusy(msg, duration);
        }
        else if(actionType.equals(ACTION_HIDE_BUSY_DIALOG.toLowerCase()))
        {
            //DONE
            hideBusy();
        }
        else if(actionType.equals(ACTION_OPEN_URL_IN_APP))
        {
            //DONE
            String link = actionMap.get(ATTRIBUTE_URL.toLowerCase());
            if(link.startsWith("http")==false)
                link ="http://"+link;

            String title = actionMap.get(ATTRIBUTE_TITLE);
            String subTitle = actionMap.get(ATTRIBUTE_SUB_TITLE);
            openURLInAPP(link, title, subTitle);
        }
        else if (actionType.equals(ACTION_OPEN_URL_CHROME))
        {
            //DONE
            String link = actionMap.get(ATTRIBUTE_URL.toLowerCase());
            if(link.startsWith("http")==false)
                link ="http://" + link;
            openChromeTab(link);
        }
        else
            perform(actionType,actionMap);
    }

    public void perform(String type,Map<String,String> params)
    {

    }

    public ActionData parseAction(String action) {
        String actionType = action.substring(0, action.indexOf("?"));
        actionType.toLowerCase();
        String valuesRaw = action.substring(action.indexOf("?") + 1, action.length());
        String[] values = action.substring(action.indexOf("?") + 1, action.length()).split("&");
        String key;
        String varValue;
        Map<String, String> actionMap = new HashMap<>();
        for (String entry : values) {
            String[] splitVal = entry.split("=");
            key = splitVal[0];
            varValue = splitVal[1];
            actionMap.put(key, varValue);
        }
        ActionData actionData=new ActionData();
        actionData.params=actionMap;
        actionData.type=actionType;
        return actionData;
    }
    public static class ActionData
    {
        String type;
        Map<String,String> params;
    }
}
