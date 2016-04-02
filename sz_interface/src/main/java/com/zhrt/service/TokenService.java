package com.zhrt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.util.date.DateUtil;
import com.zhrt.dao.TokenDAO;
import com.zhrt.entity.Token;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class TokenService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(TokenService.class);

	@Autowired
	private TokenDAO tokenDao;

	@Transactional
	public Token getById(String id) {
		Token token = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		token = tokenDao.get(paramMap);
		return token;
	}

	@Transactional
	public List<Token> getList() {
		List<Token> tokenList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		tokenList = tokenDao.getList(paramMap);
		return tokenList;
	}

	@Transactional
	public List<Token> getList(Map<String, Object> paramMap) {
		List<Token> tokenList = null;
		tokenList = tokenDao.getList(paramMap);
		return tokenList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		listCount = tokenDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(Token tokenInfo) {
		Date now = DateUtil.getCurDate();
		tokenInfo.setCreateTime(now);
		tokenInfo.setExpireTime(DateUtil.getDayAfterDate(now, 30));
		tokenDao.add(tokenInfo);
	}

	@Transactional
	public void update(Token tokenInfo) {
		tokenDao.update(tokenInfo);
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		tokenDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		tokenDao.delBatch(idsMap);
	}
}
