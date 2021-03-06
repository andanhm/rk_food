package app.andanhm.foodworld.device;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import app.andanhm.foodworld.utils.Log;

/**
 * To check the mobile phone is rooted
 */

class CheckPhoneRooted {
    private static String TAG= CheckPhoneRooted.class.getSimpleName();
    /**
     * checking rooted or not by 'android.os.Build.TAGS' contains test-keys
     *
     * @return boolean
     */
    boolean checkRootMethod1() {
        try {
            String buildTags = android.os.Build.TAGS;
            return buildTags != null && buildTags.contains("test-keys");
        }catch (Exception e){
            Log.e(TAG, String.format("%s : %s", new Object(){}.getClass().getEnclosingMethod().getName(), e.getMessage()));
        }
        return false;
    }

    /**
     *
     * checking rooted or not by if one of these file exists "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
     * "/system/bin/failsafe/su", "/data/local/su"
     *
     * @return boolean
     */
     boolean checkRootMethod2() {
        try {
            String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                    "/system/bin/failsafe/su", "/data/local/su"};
            for (String path : paths) {
                if (new File(path).exists()) return true;
            }

        }catch (Exception e){
            Log.e(TAG, String.format("%s : %s", new Object(){}.getClass().getEnclosingMethod().getName(), e.getMessage()));
        }
        return false;
    }

    /**
     *  checking rooted or not by checking executable files are running in {"/system/xbin/which", "su"}
     *
     * @return boolean
     */
    boolean checkRootMethod3() {
        try {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                return in.readLine() != null;
            } catch (Throwable t) {
                return false;
            } finally {
                if (process != null) process.destroy();
            }
        }catch (Exception e){
            Log.e(TAG, String.format("%s : %s", new Object(){}.getClass().getEnclosingMethod().getName(), e.getMessage()));
        }
        return false;
    }
}
