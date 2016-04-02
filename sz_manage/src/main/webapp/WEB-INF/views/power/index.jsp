<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<html>
  <head>
    <meta charset="utf-8">
    <title>Bootstrap Admin</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" type="text/css" href="${ctx }/webresources/common/lib/bootstrap/css/bootstrap.css" >
    <link rel="stylesheet" type="text/css" href="${ctx }/webresources/common/stylesheets/theme.css" >
    <link rel="stylesheet" type="text/css" href="${ctx }/webresources/common/lib/font-awesome/css/font-awesome.css" >
    
    <link rel="stylesheet" type="text/css" href="${ctx }/webresources/system/css/login.css" >
    <script type="text/javascript" src="${ctx }/webresources/common/js/jquery-1.7.2.min.js" ></script>
  </head>

  <body class=""> 
    
    <jsp:include page="../common/header.jsp" />
    
    <jsp:include page="../common/left.jsp" />
    
    <jsp:include page="../common/right.jsp" />
    
    


    <script src="${ctx }/webresources/common/lib/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
    </script>
    
  </body>
</html>
