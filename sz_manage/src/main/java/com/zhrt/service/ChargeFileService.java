package com.zhrt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.util.date.DateUtil;
import com.zhrt.bo.InfoResVO;
import com.zhrt.entity.ChannelAppInfo;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.PropInfo;
import com.zhrt.entity.Spcode;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class ChargeFileService {
	private static Logger logger = LoggerFactory.getLogger(ChargeFileService.class);
	
	@Autowired
	private ChannelAppInfoService channelAppInfoService;
	@Autowired
	private PropInfoService propInfoService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private PropSpcodeService propSpcodeService;
	
	@Transactional
	public List<InfoResVO> getChargeFile(Integer chanAppVerSeq){
		try {
			ChannelAppInfo channelAppInfo = channelAppInfoService.getByChanAppVerSeq(chanAppVerSeq);
			if(channelAppInfo != null){
				return getChargeFile(channelAppInfo.getChannelId(),channelAppInfo.getAppId(),null);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	public List<InfoResVO> getChargeFile(Integer chanAppVerSeq,String tel){
		try {
			ChannelAppInfo channelAppInfo = channelAppInfoService.getByChanAppVerSeq(chanAppVerSeq);
			if(channelAppInfo != null){
				return getChargeFile(channelAppInfo.getChannelId(),channelAppInfo.getAppId(),tel);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	public List<InfoResVO> getChargeFile(String channelId,String appId){
		return getChargeFile(channelId, appId, null);
	}
	
	@Transactional
	public List<InfoResVO> getChargeFile(String channelId,String appId,String tel){
		List<InfoResVO> infoResVOList = new ArrayList<InfoResVO>();
		try {
			
			String channelSeq = "";
			ChannelInfo channelInfo = channelInfoService.getById(channelId);
			if(channelInfo != null){
				channelSeq = channelInfo.getChannelId();
			}
			
			if(StringUtils.isNotBlank(appId)){
				//查询出该产品下所有道具
				List<PropInfo> propList = propInfoService.getPropsByAppId(appId);
				
				if(propList != null && propList.size() > 0){					
					
					//查询出每个道具分配的计费通道
					List<Spcode> spcodeAllVali = new LinkedList<Spcode>();
					for(PropInfo propInfo : propList){
						String sql = "select s.* from infospcode s where s.id in (select ps.spcodeId from infopropspcode ps where ps.propId='"+propInfo.getId()+"')";
						List<Spcode> spcodeList = propSpcodeService.dynamicSql(sql);
						spcodeAllVali.addAll(spcodeList);
					}
					
					//组装计费方案
					for(Spcode spcode : spcodeAllVali){
						InfoResVO infoResVO = new InfoResVO();
						
						infoResVO.setFeeCode(spcode.getFeeCode()+channelSeq);
						infoResVO.setFeeNumber(spcode.getFeeNumber());
						infoResVO.setFeeStatus(spcode.getFeeStatus());
						infoResVO.setInterceptWord(spcode.getInterceptWord());
						infoResVO.setId(UUID.randomUUID().toString().replace("-", ""));
						infoResVO.setReplyType(spcode.getReplyType());
						infoResVO.setReplyContent(spcode.getReplyContent());
						infoResVO.setTel("");
						infoResVO.setChargeTimes(spcode.getChargeTimes());
						
						infoResVOList.add(infoResVO);
					}
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return infoResVOList;
	}
}
