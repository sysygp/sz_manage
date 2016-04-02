package com.zhrt.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhrt.bo.LatnVO;
import com.zhrt.dao.LatnDao;
import com.zhrt.entity.Latn;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class LatnService {
	private static Logger logger = LoggerFactory.getLogger(LatnService.class);

	@Autowired
	private LatnDao latnDao;
	
	@Transactional
	public Latn getById(String latnId){
		Latn latn = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("latnId", latnId);
			latn = latnDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return latn;
	}
	
	
	@Transactional
	public List<Latn> getList(){
		List<Latn> latnList = null;
		try {
			Map paramMap = new HashMap();
			latnList = latnDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return latnList;
	}
	
	@Transactional
	public List<Latn> getProList(){
		List<Latn> latnList = null;
		try {
			Map latnQMap = new HashMap();
			latnQMap.put("latnLevel", "2");
			latnList = latnDao.getList(latnQMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return latnList;
	}
	
	@Transactional
	public List<Latn> getList(Map paramMap) {
		List<Latn> latnList = null;
		try {
			latnList = latnDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return latnList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			listCount = latnDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(Latn latn)   {
		try {
			latnDao.add(latn);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void update(Latn latn)   {
		try {
			latnDao.update(latn);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Transactional
	public void delById(String latnId){
		try {
			latnDao.delById(latnId);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids){
		try {
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			latnDao.delBatch(idsMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取归属地信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Map<Integer, LatnVO> getLatnMap() throws Exception{
		Map<Integer, LatnVO> latnVOMap = new HashMap<Integer,LatnVO>();
		
		try {
			List<Latn> latnList = this.getList();
			Map<Integer, Latn> latnmap = new HashMap<Integer,Latn>();
			for(Latn latn: latnList){
				latnmap.put(latn.getLatnId(), latn);
			}
			
			
			Iterator itr = latnmap.entrySet().iterator();
			while(itr.hasNext()){
				Map.Entry<Integer, Latn> entry = (Map.Entry<Integer, Latn>)itr.next();
				Latn latn = entry.getValue();
				Latn parentLatn = latnmap.get(latn.getParentLatnId());
				
				LatnVO LatnVO = new LatnVO();
				BeanUtils.copyProperties(latn, LatnVO);
				if(parentLatn != null){
					LatnVO.setParentLatnId(parentLatn.getLatnId());
					LatnVO.setParentLatnName(parentLatn.getLatnName());
					LatnVO.setParentLatnLevel(parentLatn.getLatnLevel());
				}
				
				latnVOMap.put(LatnVO.getLatnId(), LatnVO);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return latnVOMap;
	}
	
	@Transactional
	public Map<Integer, LatnVO> getProMap() throws Exception{
		Map<Integer, LatnVO> latnVOMap = new HashMap<Integer,LatnVO>();
		
		try {
			List<Latn> latnList = getProList();
			Map<Integer, Latn> latnmap = new HashMap<Integer,Latn>();
			for(Latn latn: latnList){
				latnmap.put(latn.getLatnId(), latn);
			}
			
			
			Iterator itr = latnmap.entrySet().iterator();
			while(itr.hasNext()){
				Map.Entry<Integer, Latn> entry = (Map.Entry<Integer, Latn>)itr.next();
				Latn latn = entry.getValue();
				Latn parentLatn = latnmap.get(latn.getParentLatnId());
				
				LatnVO LatnVO = new LatnVO();
				BeanUtils.copyProperties(latn, LatnVO);
				if(parentLatn != null){
					LatnVO.setParentLatnId(parentLatn.getLatnId());
					LatnVO.setParentLatnName(parentLatn.getLatnName());
					LatnVO.setParentLatnLevel(parentLatn.getLatnLevel());
				}
				
				latnVOMap.put(LatnVO.getLatnId(), LatnVO);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return latnVOMap;
	}
	
	public static void main(String[] args) {
		Map latnmap = new HashMap();
		latnmap.put("1", "1111");
		latnmap.put("2", "2222");
		
		Iterator itr = latnmap.entrySet().iterator();
		while(itr.hasNext()){
			Map.Entry<String, String> entry = (Map.Entry<String, String>)itr.next();
			System.out.println(entry.getKey()+":"+entry.getValue());;
		}
	}
}
