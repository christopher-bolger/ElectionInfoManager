package utility;

public final class Utilities {
    public static final String[] IMAGE_FORMATS = {
            ".jpg", ".jpeg", ".png", ".gif", ".bmp"
    };
    public static boolean isWebsiteURL(String url) {
        if(url == null || url.isEmpty())
            return false;
        boolean valid = url.startsWith("http://") && url.startsWith("https://");
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
}
