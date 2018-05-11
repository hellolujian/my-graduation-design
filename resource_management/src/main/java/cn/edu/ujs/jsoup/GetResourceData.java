package cn.edu.ujs.jsoup;

import cn.edu.ujs.entity.Resource;
import cn.edu.ujs.util.TimeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/5/5.
 */
public class GetResourceData {

    public Document connect(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Resource> getData(String category, String type, String point, String sort, String pageNum, String keyword) {

        List<Resource> resourceList = new ArrayList<>();
        StringBuffer dataUrlPrefix = new StringBuffer("https://download.csdn.net/search");
        String url = dataUrlPrefix
                .append("/")
                .append(category)
                .append("/")
                .append(type)
                .append("/")
                .append(point)
                .append("/")
                .append(sort)
                .append("/")
                .append(pageNum)
                .toString();

        if (!keyword.equals("null")) {
            url = url + "/" + keyword;
        }
        try {
            Document document = connect(url);
            Elements elements = document.getElementsByClass("album_detail_list clearfix");
            Integer userId = 1;
            for (Element element : elements) {

                Resource resource = new Resource();
                String iconUrl = element.selectFirst("dt a img").attr("src");
                resource.setSuffixName("." + iconUrl.substring(iconUrl.lastIndexOf("/") + 1, iconUrl.lastIndexOf(".")));
                resource.setTitle(element.selectFirst(".album_detail_title").text());
                resource.setDescription(element.selectFirst(".cate_search_p").text());
                Elements labels = element.getElementsByTag("label");
                resource.setSize(labels.get(1).selectFirst("em").text());
                String dateStr = labels.get(2).selectFirst("em").text();
                Date date = TimeUtil.longStrToDate(labels.get(2).selectFirst("em").text());
                resource.setCreateTime(date);
                String detailUrl = element.getElementsByTag("a").get(0).attr("href");
                StringBuffer detailUrlPrefix = new StringBuffer("https://download.csdn.net");
                url = detailUrlPrefix.append(detailUrl).toString();
                Document detail = connect(url);
                Elements tags = detail.getElementsByClass("tag");
                List<String> tagList = new ArrayList<>();
                for (Element tag : tags) {
                    tagList.add(tag.text());
                }
                resource.setTagList(tagList);
                resource.setPoints(new Integer(detail.selectFirst(".dl_download label em").text()));
                resource.setTypeId(new Integer(type));
                resource.setUserId(userId);
                userId++;
                resourceList.add(resource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resourceList;
    }
}
