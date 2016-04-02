package com.zhrt.service;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhrt.entity.Billing;
import com.zhrt.entity.ChannelAppInfo;
import com.zhrt.entity.Spinfo;
import com.zhrt.util.Constant;

/**
 * 账户信息业务层
 *
 */
@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class DealBillService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(DealBillService.class);

	@Autowired
	private BillingService billingService;
	@Autowired
	private SpinfoService spinfoService;
	@Autowired
	private ChannelAppInfoService channelAppInfoService;
	
	@Transactional
	public String addBill(String spid,Map paramMap) throws Exception {
		String flag = "ok";
		
		try {
			
			String service_synType = "";//1:get方式;2:post方式
			String service_codeLen = "";//该业务下所有道具指令的长度
			String service_f_linkid = "";//流水同步中流水号字段
			String service_f_msg = "";//流水同步中短信内容字段
			String service_f_spnumber = "";//流水同步中spnumber字段
			String service_f_mobile = "";//流水同步中手机号字段
			String service_f_status = "";//流水同步中状态字段
			String service_f_ext = "";//流水同步中扩展字段
			
			//根据serviceId查询该业务流水同步的格式
			Spinfo spInfo = spinfoService.getById(spid);
			if(spInfo != null){
				int synType = spInfo.getSynType();
//				int codeLen = spInfo.getCodeLen();
				
				service_synType = spInfo.getSynType()+"";
				service_codeLen = spInfo.getCodeLen()+"";
				service_f_linkid = spInfo.getF_linkid()+"";
				service_f_msg = spInfo.getF_msg()+"";
				service_f_spnumber = spInfo.getF_spnumber()+"";
				service_f_mobile = spInfo.getF_mobile()+"";
				service_f_status = spInfo.getF_status()+"";
				service_f_ext = spInfo.getF_ext()+"";
				
				
				String spLinkid = "";//同步流水中sp的订单号
				String chargeMsg = "";//同步流水中完整的计费指令
				String chargeSpnum = "";//同步流水中完整的计费短信端口
				String chargeMobile = "";//同步流水中计费手机号
				String chargeStatus = "";//同步流水中计费状态
				
				//get方式
				if(Constant.SYN_TYPE_GET.equals(service_synType)){
					spLinkid = paramMap.get(service_f_linkid)==null?"":paramMap.get(service_f_linkid).toString();
					chargeMsg = paramMap.get(service_f_msg)==null?"":paramMap.get(service_f_msg).toString();
					chargeSpnum = paramMap.get(service_f_spnumber)==null?"":paramMap.get(service_f_spnumber).toString();
					chargeMobile = paramMap.get(service_f_mobile)==null?"":paramMap.get(service_f_mobile).toString();
					chargeStatus = paramMap.get(service_f_status)==null?"":paramMap.get(service_f_status).toString();
					if("DELIVRD".equals(chargeStatus)){
						chargeStatus="0";
					}
				}
				//post 方式
				else if(Constant.SYN_TYPE_POST.equals(service_synType)){
					
				}
				
				Billing billing = new Billing();
				billing.setLinkId(spLinkid);
				billing.setMsg(chargeMsg);
				billing.setTel(chargeMobile);
				billing.setPrice(0);
				billing.setStatus(chargeStatus);
				billing.setSpId(spid);
				
//				String chanAppSeq = chargeMsg.substring(codeLen,codeLen+3);
//				ChannelAppInfo channelAppInfo = channelAppInfoService.getByChanAppVerSeq(Integer.parseInt(chanAppSeq));
//				if(channelAppInfo != null){
//					billing.setSpId(spid);
////					billing.setSpCodeId();
//					billing.setCpId(channelAppInfo.getCpId());
//					billing.setServiceId(channelAppInfo.getAppId());
////					billing.setPropId(channelAppInfo.getp);
//				}
				
				billing.setChargeTime(new Date());
				billing.setcTime(new Date());
				billingService.add(billing);
				if(StringUtils.isNotBlank(spInfo.getF_answer())){					
					flag = spInfo.getF_answer();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
}
