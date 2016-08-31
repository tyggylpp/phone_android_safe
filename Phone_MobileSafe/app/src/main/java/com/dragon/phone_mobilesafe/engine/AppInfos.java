package com.dragon.phone_mobilesafe.engine;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.dragon.phone_mobilesafe.activity.AppManagerActivity;
import com.dragon.phone_mobilesafe.bean.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016-08-24.
 */
public class AppInfos {
    public  static List<AppInfo>  getAppInfos(Context context)
    {

        List<AppInfo> listApps=new ArrayList<AppInfo>();

        //获取包管理者
        // flag additional android中类似这种标识 默认值都可以填写0
        PackageManager packageManager=context.getPackageManager();

        //List<Insta> packageManager.getInstalledPackages(0);
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (PackageInfo info:installedPackages)
        {
            AppInfo appInfo=new AppInfo();
           Drawable drawable= info.applicationInfo.loadIcon(packageManager);
            appInfo.setIcon(drawable);
           String apkName= info.applicationInfo.loadLabel(packageManager).toString();
            appInfo.setApkName(apkName);
           String apkPackage= info.packageName;
            appInfo.setApkPackageName(apkPackage);
            //apk 资源路径
            String sourceDir = info.applicationInfo.sourceDir;
            File file=new File(sourceDir);
            long apkSize=file.length();
            appInfo.setApkSize(apkSize);
            //程序安装应用程序标记
            int flags= info.applicationInfo.flags;
            if((flags&ApplicationInfo.FLAG_SYSTEM)!=0)
            {
                appInfo.setUserApp(false);
            }else {
                appInfo.setUserApp(true);
            }
            if((flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)!=0)
            {
                //sd
                appInfo.setRom(false);
            }else
            {
                //rom
                appInfo.setRom(true);
            }
            listApps.add(appInfo);
        }
        return listApps;

    }
}
