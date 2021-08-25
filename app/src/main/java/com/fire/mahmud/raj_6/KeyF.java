package com.fire.mahmud.raj_6;

public class KeyF {
    public static enum firebase{
        Users,Passwords,Report;
    }
    public static class groupchild{
        public static String biddhuth="Biddhuth";
        public static String shasthoBabostha="ShasthoBabostha";
        public static String sikkha="Shikkha";
        public static String nirapotta="Nirapottha";
        public static String samajikunnaon="SamajiUnnoan";
        public static String colomanKarrjokrom="ColomanKarrjokrom";
        public static String sarakbabosstha="SarakBabosstha";
        public static String galary="হোম";
        public static String report="Allreport";
        public static String banglaLikha(String key){
            switch (key){
                case "Biddhuth":
                    return "বিদ্যুৎ";
                case "ShasthoBabostha":
                    return "স্বাস্থ্য ব্যবস্থা";
                case "Shikkha":
                    return "শিক্ষা";
                case "Nirapottha":
                    return "নিরাপত্তা";
                case  "SamajiUnnoan":
                    return "সামাজিক উন্নয়ন";
                case "ColomanKarrjokrom":
                    return "চলমান কার্যক্রম";
                case "SarakBabosstha":
                    return "সড়ক এবং যোগাযোগ";

            }
            return "";
        }
    }
    public static class Intentdata{
        public static String title="Title";
        public static String details="Details";
        public static String picUri="PicUriData";
    }
    public static class AllstepIntent{
        public static String folder="FolderName";
        public static String subfolder="SubFolder";
    }
    public static String getCompressedText(String text,int end){
        return text.length()>end?text.substring(0,end)+"...":text;
    }
    public static String notice="Notice";
    public static String gallery="AllThePictureOnly";
    public static String simpleUploadPicture="PictureWithDetails";
    public static String simpleUploadData="DatabasePathForData";
    public static String allUpload="AllUpload";
}
