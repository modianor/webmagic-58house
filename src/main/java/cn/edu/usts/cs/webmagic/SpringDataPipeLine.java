package cn.edu.usts.cs.webmagic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.usts.cs.webmagic.pojo.HouseInfo;
import cn.edu.usts.cs.webmagic.service.HouseInfoService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


@Component
public class SpringDataPipeLine implements Pipeline {
	@Autowired
	HouseInfoService his;
	@Override
	public void process(ResultItems resultItems, Task task) {
		HouseInfo info = resultItems.get("houseinfo");
		if (his != null)
			his.save(info);
	}

}
