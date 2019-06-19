package wallpaper.com.fullscreenstatus;

public class Video_detail {

String title,category,singer,video,trending;

public Video_detail(){



}


    public String getTrending() {
        return trending;
    }

    public void setTrending(String trending) {
        this.trending = trending;
    }

    public Video_detail(String title, String category, String singer, String video, String trending) {
        this.title = title;
        this.category = category;
        this.singer = singer;
        this.video = video;
        this.trending=trending;

    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
