<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/cpinfo.js" ></script>
	<script type="text/javascript">
	  $(function(){
		 // alert("122");
		 //给所有的奇数td标签加上class属性
		 $("tr td:nth-child(2n-1)").attr("class", "addColor");
		 $("tr td:nth-child(2n-1) label").attr("class","addRight");
		 $("tr td:nth-child(2n) label").attr("class","addCenter");
		 
	  })
	
	</script>
	
	<style type="text/css">
	  .addColor{
	    background-color: #bcbcbc;
	    width: 25%;
	    
	  }
	 .addRight{
	 text-align: right;
	 }
	.addCenter{
	 text-align: center;
	}
	</style>
	
	
</head>

<body>
   <div style="color: red;text-align:center;">${errorInfo }</div>
   <div class="well">
     <table border="1" class="table" width="80%">
      <tr>
        <th colspan="4" style="text-align: center;background-color: #bcbcbc;">平台信息总览</th>
      </tr>
      <tr>
         <td><label>渠道数量&nbsp;:</label></td>
         <td><label>${platCountNap.channelCount }</label></td>
         <td><label>cp数量&nbsp;:</label></td>
         <td><label>${platCountNap.cpCount }</label></td>
      </tr>
      <tr>
         <td><label>运营产品数量&nbsp;:</label></td>
         <td><label>${platCountNap.appCount }</label></td>
         <td><label>用户系统正式用户&nbsp;:</label></td>
         <td><label>${platCountNap.userZBCount }</label></td>
      </tr>
      <tr>
         <td><label>用户系统游客用户&nbsp;:</label></td>
         <td><label>${platCountNap.userNBCount }</label></td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
      </tr>
      <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
      </tr>
      <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
      </tr>
      <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
      </tr>     
      <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
      </tr>     
      <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
      </tr>     
      <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
      </tr>     
      <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
      </tr>     
      <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
      </tr>     
     </table>
   </div>
</body>
</html>