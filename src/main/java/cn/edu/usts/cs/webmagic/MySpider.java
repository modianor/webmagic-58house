package cn.edu.usts.cs.webmagic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.edu.usts.cs.TTFont.TTFontApp;
import cn.edu.usts.cs.webmagic.constant.Constant;
import cn.edu.usts.cs.webmagic.pojo.HouseInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class MySpider implements PageProcessor {

	private Site site = Site.me().setDomain("58.com").setCharset("utf-8").setSleepTime(3000).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	private static Random rand = new Random(1234);

	public static Integer getTime() {
		int nextInt = rand.nextInt(5);
		return (10 - nextInt) * 1000;
	}

	int count = 1;

	public void process(Page page) {
		site.setSleepTime(getTime());
		List<String> all_links = page.getHtml().css("ul.house-list li.house-cell div.des h2 a").links().all();
		Set<String> links_set = new TreeSet<String>();
		links_set.addAll(all_links);
		links_set.remove("https://e.58.com/all/zhiding.html");
		all_links = new ArrayList<String>(links_set);
		page.addTargetRequests(all_links);

		if (all_links.size() == 0) {
			try {
				saveInfo(page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			page.addTargetRequests(all_links);
	}

	public void saveInfo(Page page) throws Exception {
		String content = page.getHtml().get();
		String house_title = Jsoup.parse(content).select(Constant.Selector.house_title).text().trim().toString();
		String house_price = Jsoup.parse(content).select(Constant.Selector.house_price).text().trim().toString();
		//		String price_type = Jsoup.parse(content).select(Constant.Selector.price_type).text().trim().toString();
		String lease_way = Jsoup.parse(content).select(Constant.Selector.lease_way).text().trim().toString();
		String house_type = Jsoup.parse(content).select(Constant.Selector.house_type).text().trim().toString();
		String toward_floor = Jsoup.parse(content).select(Constant.Selector.toward_floor).text().trim().toString();
		String house_estate = Jsoup.parse(content).select(Constant.Selector.house_estate).text().trim().toString();
		String house_area_1 = Jsoup.parse(content).select(Constant.Selector.house_area_1).text().trim().toString();
		String house_area_2 = Jsoup.parse(content).select(Constant.Selector.house_area_2).text().trim().toString();
		String house_area = house_area_1 + " " + house_area_2;
		String detailed_address = Jsoup.parse(content).select(Constant.Selector.detailed_address).text().trim()
				.toString();
		String encrytion_str = getEncryptionString(content);

		TTFontApp ttf = new TTFontApp(encrytion_str);
		Integer realPrice = Integer.parseInt(ttf.cleanData(house_price));
		house_title = ttf.cleanData(house_title);
		house_type = ttf.cleanData(house_type);
		toward_floor = ttf.cleanData(toward_floor);
		page.putField("encrytion_str", encrytion_str);

		HouseInfo info = HouseInfo.builder().house_estate(house_estate).house_title(house_title).house_type(house_type)
				.house_price(realPrice).lease_way(lease_way).toward_floor(toward_floor)
				.detailed_address(detailed_address).house_area(house_area).build();

		page.putField("houseinfo", info);
	}

	public static String getEncryptionString(String all_str) {
		int start = all_str.indexOf(";base64,") + ";base64,".length();
		int end = all_str.indexOf("') format('truetype')");
		return all_str.substring(start, end);

	}

	public Site getSite() {
		return site;
	}

	@Autowired
	SpringDataPipeLine springdata_pipeline;

	@Scheduled(fixedDelay = 10000, initialDelay = 1000)
	public void process() {
		Spider.create(new MySpider()).addUrl("https://sh.58.com/chuzu/").addPipeline(springdata_pipeline).thread(2)
				.run();
	}
}
