package cn.edu.usts.cs.webmagic.service.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.usts.cs.webmagic.dao.HouseInfoDao;
import cn.edu.usts.cs.webmagic.pojo.HouseInfo;
import cn.edu.usts.cs.webmagic.service.HouseInfoService;

@Service
public class HouseInfoServiceImp implements HouseInfoService {
	@Autowired
	private HouseInfoDao houseInfoDao;

	@Override
	@Transactional
	public void save(HouseInfo info) {
		HouseInfo param = HouseInfo.builder().build();
		param.setHouse_title(info.getHouse_title());
		List<HouseInfo> all = findHouseInfo(param);
		if (all.size() == 0) {
			this.houseInfoDao.saveAndFlush(info);
		}
	}

	@Override
	public List<HouseInfo> findHouseInfo(HouseInfo info) {
		Example<HouseInfo> example = Example.of(info);
		List<HouseInfo> all = this.houseInfoDao.findAll(example);
		return all;
	}

}
