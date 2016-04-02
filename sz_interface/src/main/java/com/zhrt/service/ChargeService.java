package com.zhrt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.util.date.DateUtil;
import com.system.util.property.PropertiesConfigDynamic;
import com.zhrt.bo.InfoReqVO;
import com.zhrt.bo.InfoResVO;
import com.zhrt.bo.ResultReqVO;
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.Billingterminal;
import com.zhrt.entity.ChannelAppInfo;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.CpInfo;
import com.zhrt.entity.Spcode;
import com.zhrt.entity.Userinfo;
import com.zhrt.util.PhoneUtil;

/**
 * 账户信息业务层
 *
 */
@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class ChargeService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ChargeService.class);

	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private ChargeFileService chargeFileService;
	@Autowired
	private ChannelAppInfoService channelAppInfoService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private CpInfoService cpInfoService;
	@Autowired
	private SpcodeService spcodeService;
	@Autowired
	private BillingterminalService billingterminalService;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public List<InfoResVO> generateChargeinfo(InfoReqVO infoReqVO) throws Exception {
		List<InfoResVO> infoResVOList = new ArrayList<InfoResVO>();
		try {
			
			String imsiP = infoReqVO.getImsi();
			String uuidP = infoReqVO.getUuid();
			
			
			Userinfo userinfo = userinfoService.getByUuid(uuidP);
			
			//已注册则下发计费通道
			String channelAppSeq = infoReqVO.getChannelapp();
			if(userinfo != null){
				List<InfoResVO> infoResVOListCharge = chargeFileService.getChargeFile(infoReqVO,Integer.parseInt(channelAppSeq),userinfo);
				if(infoResVOListCharge !=null && infoResVOListCharge.size()>0){					
					infoResVOList.addAll(infoResVOListCharge);
				}
			}else{
				String imsi = null;
				if(StringUtils.isBlank(imsiP)){
					imsi = "000000000000000";
				}else{
					imsi = imsiP;
				}
				String feeCode = imsi+"#"+uuidP+"#"+channelAppSeq;
				
				//保存注册用户信息
				Map registMap =new HashMap();
				registMap.put("mobile", "");
				registMap.put("content", feeCode);
				regist(registMap);
				
				
				//生成注册报文
				InfoResVO infoResVO = new InfoResVO();
				infoResVO.setId(infoReqVO.getUuid());
				infoResVO.setFeeStatus("0");
			 	infoResVO.setFeeNumber(PropertiesConfigDynamic.getConfig("registPhoNum"));
			 	infoResVO.setFeeCode(feeCode);
				
				//下发注册信息
				infoResVOList.add(infoResVO);
				//下发计费方案
				List<InfoResVO> infoResVOListCharge = chargeFileService.getChargeFile(infoReqVO,Integer.parseInt(channelAppSeq),null);
				if(infoResVOListCharge !=null && infoResVOListCharge.size()>0){					
					infoResVOList.addAll(infoResVOListCharge);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return infoResVOList;
	}

	
	/**
	 * 注册
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean regist(Map map) throws Exception {
		boolean flag = false;
		System.out.println("regist method");
		try {
			
			String mobile = map.get("mobile")==null?"":map.get("mobile").toString();
			String content = map.get("content")==null?"":map.get("content").toString();
			
			
			if(StringUtils.isNotBlank(content) && content.contains("#")){
				String imsi = content.split("#")[0];
				String uuid = content.split("#")[1];
				String chanAppSeq = "";
				ChannelAppInfo channelAppInfo = null;
				try{
					chanAppSeq = content.split("#")[2];
					if(StringUtils.isNotBlank(chanAppSeq)){						
						channelAppInfo = channelAppInfoService.getByChanAppVerSeq(Integer.parseInt(chanAppSeq));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
//				if(!ValiUtil.valiImei(imsi)){
//					System.out.println("regist 接口请求imsi参数不合法");
//					return flag;
//				}
				Integer provinceId = null;
				if(StringUtils.isNotBlank(mobile)){					
					provinceId = PhoneUtil.getProvinceIdByPhone(mobile);
				}
				
				Userinfo userinfoOld = userinfoService.getByUuid(uuid);
				if(userinfoOld != null){
					userinfoOld.setImsi(imsi);
					if(provinceId != null){
						userinfoOld.setProvince(provinceId+"");					
					}
					userinfoOld.setTel(mobile);
					userinfoService.update(userinfoOld);
				}else{
					Userinfo userinfo = new Userinfo();
					userinfo.setcTime(new Date());
					userinfo.setImsi(imsi);
					if(provinceId != null){
						userinfo.setProvince(provinceId+"");					
					}
					userinfo.setTel(mobile);
					userinfo.setUuid(uuid);
					userinfo.setChanAppSeq(chanAppSeq);
					if(channelAppInfo != null){
						userinfo.setChannelId(channelAppInfo.getChannelId());
						userinfo.setAppId(channelAppInfo.getAppId());				
					}
					userinfoService.add(userinfo);
				}
				
				flag = true;
			}
			
		}catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	
	@Transactional
	public void result(ResultReqVO resultReqVO) throws Exception {

		try {
			//userUuid+"#"+spcode.getId()+"#"+chanAppVerSeq
			String idP = resultReqVO.getId();
			
			if(StringUtils.isNotBlank(idP)){
				//根据idP查询出流水信息
				String[] ids =idP.split("#");
				String userUuid = ids[0];
				String spCodeId = ids[1];
				String chanAppSeq = ids[2];
				
				
				Billingterminal billingterminal = new Billingterminal();
				billingterminal.setcTime(new Date());
				billingterminal.setChanAppSeq(chanAppSeq);
				
				if(StringUtils.isNotBlank(chanAppSeq)){
					//渠道
					ChannelAppInfo channelAppInfo = channelAppInfoService.getByChanAppVerSeq(Integer.parseInt(chanAppSeq));
					if(channelAppInfo != null){						
						billingterminal.setChannelId(channelAppInfo.getChannelId());
						ChannelInfo chan = channelInfoService.getById(channelAppInfo.getChannelId());
						if(chan != null){							
							billingterminal.setChannelName(chan.getCnName());
						}
					}
					
					//产品
					billingterminal.setServiceId(channelAppInfo.getAppId());
					AppInfo appInfo = appInfoService.getById(channelAppInfo.getAppId());
					if(appInfo !=null){
						billingterminal.setServiceName(appInfo.getAppName());
						
						String cpId = appInfo.getCpId();
						billingterminal.setCpId(cpId);
						CpInfo cpInfo = cpInfoService.getById(cpId);
						billingterminal.setCpName(cpInfo.getCpName());
					}
					billingterminal.setPropId(null);
					
				}
				
				//通道
				billingterminal.setSpCodeId(spCodeId);
				Spcode spcode = spcodeService.getById(spCodeId);
				billingterminal.setSpCodeName(spcode.getName());
				billingterminal.setPrice(Integer.parseInt(spcode.getChargeMoney()));
				billingterminal.setSpId(spcode.getSpId());
				
				
				billingterminal.setUserUuid(userUuid);
				Userinfo userinfoOld = userinfoService.getByUuid(userUuid);
				if(userinfoOld != null){
					billingterminal.setTel(userinfoOld.getTel());
				}
				
				billingterminalService.add(billingterminal);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
