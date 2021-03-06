package yycg.base.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yycg.base.pojo.po.Dictinfo;
import yycg.base.pojo.vo.PageQuery;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;
import yycg.base.process.context.Config;
import yycg.base.process.result.DataGridResultInfo;

import yycg.base.process.result.ResultInfo;
import yycg.base.process.result.ResultUtil;
import yycg.base.process.result.SubmitResultInfo;
import yycg.base.service.SysConfigService;
import yycg.base.service.UserService;


/**
 * 
 * <p>Title: UserAction</p>
 * <p>Description:系统用户管理 </p>
 * <p>Company: www.itcast.com</p> 
 * @author	苗润土
 * @date	2014年11月26日上午10:56:09
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserAction {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	//用户查询页面
	@RequestMapping("/queryuser")
	public String queryuser(Model model)throws Exception{
		//将页面所需要的数据取出，传到页面
		
		List<Dictinfo> groupList = sysConfigService.findSysInfoByType("s01");
		
		model.addAttribute("groupList", groupList);
		
		return "/base/user/queryuser";
	}
	
	//用户查询页面的结果集
	//最终DataGridResultInfo通过@ResponseBody将java对象转成json
	@RequestMapping("/queryuser_result")
	public @ResponseBody DataGridResultInfo queryuser_result(
			SysuserQueryVo sysuserQueryVo,
			int page,//页码
			int rows//每页显示个数
			)throws Exception{
		
		//非空校验
		sysuserQueryVo = sysuserQueryVo!=null?sysuserQueryVo:new SysuserQueryVo();
		
		//查询列表的总数
		int total = userService.findSysuserCount(sysuserQueryVo);
		
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		sysuserQueryVo.setPageQuery(pageQuery);
		
		//分页查询，向sysuserQueryVo中传入pageQuery
		List<SysuserCustom> list = userService.findSysuserList(sysuserQueryVo);
		
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		//填充 total
		dataGridResultInfo.setTotal(total);
		//填充  rows
		dataGridResultInfo.setRows(list);
		
		return dataGridResultInfo;
	}
	
	//添加用户页面
	@RequestMapping("/addsysuser")
	public String addsysuser(Model model)throws Exception{
		
		return "/base/user/addsysuser";
	}
	
	@RequestMapping("/addsysusersubmit")
	public @ResponseBody SubmitResultInfo addsysusersubmit(SysuserQueryVo sysuserQueryVo) throws Exception{
		
//		ResultInfo resultInfo = new ResultInfo();
//		resultInfo.setType(ResultInfo.TYPE_RESULT_SUCCESS);
//		resultInfo.setMessage("操作成功！");
		
		userService.insertSysuser(sysuserQueryVo.getSysuserCustom());
		
//		SubmitResultInfo submitResultInfo = new SubmitResultInfo(resultInfo);
		
//		return submitResultInfo;
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}

	
	@RequestMapping("/deleteSysuser")
	public @ResponseBody SubmitResultInfo deletesysuser(String id) throws Exception{
		
		userService.deleteSyster(id);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/editsysuser")
	public String editsysuser(Model model,String id) throws Exception{
		
		SysuserCustom sysuserCustom = userService.findSysuserById(id);
		
		model.addAttribute("sysuserCustom", sysuserCustom);
		
		return "/base/user/editsysuser";
	}
	
	@RequestMapping("/editsysusersubmit")
	public @ResponseBody SubmitResultInfo editsysusersubmit(String id,SysuserQueryVo sysuserQueryVo) throws Exception{
		
		
		userService.updateSysuser(id, sysuserQueryVo.getSysuserCustom());
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	
}
