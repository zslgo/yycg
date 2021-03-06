package yycg.base.service;

import java.util.List;

import yycg.base.pojo.po.Sysuser;
import yycg.base.pojo.po.Usergys;
import yycg.base.pojo.po.Userjd;
import yycg.base.pojo.po.UserjdExample;
import yycg.base.pojo.po.Useryy;
import yycg.base.pojo.po.UseryyExample;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;

public interface UserService {

	// 根据条件查询用户信息
	public List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo)
			throws Exception;

	// 根据条件查询列表的总数
	public int findSysuserCount(SysuserQueryVo sysuserQueryVo) throws Exception;
	
	
	public Userjd findUserjdByMc(String symc) throws Exception;
	
	public Useryy findUseryyByMc(String symc) throws Exception;
	
	public Usergys findUsergysByMc(String symc) throws Exception;
	
	public void insertSysuser(SysuserCustom sysuserCustom) throws Exception;
	
	public void deleteSyster(String id) throws Exception;
	
	public void updateSysuser(String id,SysuserCustom sysuserCustom) throws Exception;
	
	public SysuserCustom findSysuserById(String id) throws Exception;
}
