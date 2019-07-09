package cn.edu.usts.cs.webmagic.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Entity
@Builder
@Getter
@Setter
@ToString
public class HouseInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long house_id;// 房屋ID
	private String house_title;// 标题
	private Integer house_price; // 房价
	private String lease_way;// 租赁方式：整租
	private String house_type;// 房屋类型：閏室麣厅麣卫 麣麣餼 平 精装修
	private String toward_floor;// 朝向楼层：南北 中层 / 麣齤层
	private String house_estate;// 所在小区：富城湾 (在租 209套 | 在售 639 套)
	private String house_area;// 所属区域：梁溪 黄巷 距离地铁1号线刘潭2478米
	private String detailed_address;// 详细地址： 广石西路 附近高薪工作 查看地图
	
	@Tolerate
	public HouseInfo () {};
}
