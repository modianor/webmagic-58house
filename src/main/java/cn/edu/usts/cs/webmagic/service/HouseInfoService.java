package cn.edu.usts.cs.webmagic.service;

import java.util.List;

import cn.edu.usts.cs.webmagic.pojo.HouseInfo;

public interface HouseInfoService {
	public void save(HouseInfo info);
	public List<HouseInfo> findHouseInfo(HouseInfo info);
}
