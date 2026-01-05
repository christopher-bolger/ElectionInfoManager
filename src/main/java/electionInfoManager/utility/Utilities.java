package electionInfoManager.utility;

import javafx.scene.image.Image;

import java.io.IOException;

public final class Utilities {
    public static final String[] IMAGE_FORMATS = {
            ".jpg", ".jpeg", ".png", ".gif", ".bmp"
    };

    public static boolean isWebsiteURL(String url) {
        if(url == null || url.isEmpty())
            return false;
        boolean valid = url.startsWith("http://") || url.startsWith("https://");
        boolean found = false;
        for(String format : IMAGE_FORMATS) {
            if(url.contains(format)) {
                found = true;
                break;
            }
        }
        if(!found)
            valid = false;
        return valid;
    }


    public static Image loadImageWithUA(String urlStr) throws IOException {
        var url = new java.net.URL(urlStr);
        var conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setInstanceFollowRedirects(true);
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(12000);
        conn.setRequestProperty("User-Agent",
                "ElectionInfoManager/1.0 (student election information system project; contact: 20111314@setu.ie)");
        // Optional but sometimes helps behind proxies:
        conn.setRequestProperty("Accept", "image/avif,image/webp,image/apng,image/*,*/*;q=0.8");
        int code = conn.getResponseCode();
        if (code != 200) throw new IOException("HTTP " + code + " from " + urlStr);

        try (var in = conn.getInputStream()) {
            return new Image(in);  // synchronous load; throws if decoding fails
        }
    }

}
