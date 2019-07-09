package cn.edu.usts.cs.webmagic.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.edu.usts.cs.webmagic.pojo.HouseInfo;

public interface HouseInfoDao extends JpaRepository<HouseInfo, Long> {

}
