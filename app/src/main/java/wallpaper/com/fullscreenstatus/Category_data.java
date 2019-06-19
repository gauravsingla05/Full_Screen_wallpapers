package wallpaper.com.fullscreenstatus;

public class Category_data {
    private String cateName;
    private String cate_image_url;

/*    public Category_data(String cateName, String cate_image_url) {
        this.cateName = cateName;
        this.cate_image_url = cate_image_url;
    }*/

    public String getCateName() {

        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCate_image_url() {
        return cate_image_url;
    }

    public void setCate_image_url(String cate_image_url) {
        this.cate_image_url = cate_image_url;
    }
}
