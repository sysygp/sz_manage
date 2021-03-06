package com.system.page;

import com.google.common.collect.Lists;
import com.system.util.Constant;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Page<T>
{
  public static final String ASC = "asc";
  public static final String DESC = "desc";
  protected int pageNo = 1;
  private int pageSize = 15;
  protected String orderBy = null;
  protected String order = null;
  protected boolean autoCount = true;

  protected List<T> result = Lists.newArrayList();
  protected long totalCount = -1L;

  public Page()
  {
  }
  
  public Page(int pageSize)
  {
	  this.pageSize = pageSize;
  }

  public int getPageNo()
  {
    return this.pageNo;
  }

  public void setPageNo(int pageNo)
  {
    this.pageNo = pageNo;

    if (pageNo < 1)
      this.pageNo = 1;
  }

  public Page<T> pageNo(int thePageNo)
  {
    setPageNo(thePageNo);
    return this;
  }

  public int getPageSize()
  {
    return this.pageSize;
  }

  public int getFirst()
  {
    return ((this.pageNo - 1) * this.pageSize + 1);
  }

  public String getOrderBy()
  {
    return this.orderBy;
  }

  public void setOrderBy(String orderBy)
  {
    this.orderBy = orderBy;
  }

  public Page<T> orderBy(String theOrderBy) {
    setOrderBy(theOrderBy);
    return this;
  }

  public String getOrder()
  {
    return this.order;
  }

  public void setOrder(String order)
  {
    String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
    for (String orderStr : orders) {
      if ((!(StringUtils.equals("desc", orderStr))) && (!(StringUtils.equals("asc", orderStr)))) {
        throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
      }
    }

    this.order = StringUtils.lowerCase(order);
  }

  public Page<T> order(String theOrder) {
    setOrder(theOrder);
    return this;
  }

  public boolean isOrderBySetted()
  {
    return ((StringUtils.isNotBlank(this.orderBy)) && (StringUtils.isNotBlank(this.order)));
  }

  public boolean isAutoCount()
  {
    return this.autoCount;
  }

  public void setAutoCount(boolean autoCount)
  {
    this.autoCount = autoCount;
  }

  public Page<T> autoCount(boolean theAutoCount) {
    setAutoCount(theAutoCount);
    return this;
  }

  public List<T> getResult()
  {
    return this.result;
  }

  public void setResult(List<T> result)
  {
    this.result = result;
  }

  public long getTotalCount()
  {
    return this.totalCount;
  }

  public void setTotalCount(long totalCount)
  {
    this.totalCount = totalCount;
  }

  public long getTotalPages()
  {
    if (this.totalCount < 0L) {
      return -1L;
    }

    long count = this.totalCount / this.pageSize;
    if (this.totalCount % this.pageSize > 0L) {
      count += 1L;
    }
    return count;
  }

  public boolean isHasNext()
  {
    return (this.pageNo + 1 <= getTotalPages());
  }

  public int getNextPage()
  {
    if (isHasNext()) {
      return (this.pageNo + 1);
    }
    return this.pageNo;
  }

  public boolean isHasPre()
  {
    return (this.pageNo - 1 >= 1);
  }

  public int getPrePage()
  {
    if (isHasPre()) {
      return (this.pageNo - 1);
    }
    return this.pageNo;
  }
  
	public void setPage(Page page, Map paramMap, String pageNoParam,String orderByParam,String orderParam) {
		int pageNo = 1;
		if (!pageNoParam.equals("")) {
			pageNo = Integer.parseInt(pageNoParam);
		}
		if (pageNo < 1) {
			pageNo = 1;
		}
		page.setPageNo(pageNo);
		int limitB = pageNo == 1 ? 0 : (pageNo - 1) * page.getPageSize();
		paramMap.put(Constant.DATABASE_LIMIT_FILED, limitB);
		paramMap.put(Constant.DATABASE_LIMI_NUM_FILED, page.getPageSize());
		paramMap.put(Constant.DATABASE_ORDERBY_FILED, orderByParam);
		paramMap.put(Constant.DATABASE_ORDER_FILED, orderParam);
	}
}