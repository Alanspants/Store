<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>商品列表</title>
</head>
<link rel="stylesheet" type="text/css" href="css/public.css">
<style type="text/css">
    table {
        border-collapse: collapse;
    }

    /*商品列表第1列*/
    .col1 {
        padding-top: 5px;
        border-top: 1px dashed #666666;
        text-indent: 40px
    }

    /*商品列表第2列*/
    .col2 {
        padding-top: 5px;
        border-top: 1px dashed #666666;
        text-align: right;
    }

    /*商品列表第3列*/
    .col3 {
        padding-top: 5px;
        border-top: 1px dashed #666666;
        text-align: center;
    }
</style>
<body>
<table width="100%" border="0" align="center">
    <tr>
        <td width="616"><img src="images/list.jpg" align="absmiddle"/></td>
        <td width="734" align="right"><img src="images/mycar1.jpg" align="absmiddle"/> <a href="controller?action=cart">&nbsp;购物车</a>
            | <a href="zhanghu.html">我的帐户</a> | <a href="controller?action=logout">账户注销</a></td>
    </tr>
</table>
<hr width="100%"/>
<div class="text3" align="center">请从商品列表中选择您喜爱的商品</div>
<br>
<table width="100%" border="0" align="center">
    <tr bgcolor="#b4c8ed">
        <th>商品名称</th>
        <th width="5%">商品价格</th>
        <th width="15%">添加到购物车</th>
    </tr>
    <c:forEach var="goods" items="${goodsList}" varStatus="status">
        <tr
                <c:if test="${status.index %2 == 0}">
                    bgcolor='#ffffff'
                </c:if>
                <c:if test="${status.index %2 != 0}">
                    bgcolor='#edf8ff'
                </c:if>
        >
            <td class="col1"><a href="controller?action=detail&id=${goods.id}">${goods.description}</a></td>
            <td class="col2">${goods.price}</td>
            <td class="col3"><a href="controller?action=add&pagename=list&id=${goods.id}&name=${goods.name}&price=${goods.price}">添加到购物车</a></td>
        </tr>
    </c:forEach>
</table>
<hr/>
<div align="center">
    <ul class="pagination">

        <li><a href="controller?action=paging&page=prev">«</a></li>
        <c:forEach var="page" begin="1" end="${totalPageNumber}">
            <li><a
                    <c:if test="${page == currentPage}">
                        class="active"
                    </c:if>
                    href="controller?action=paging&page=${page}">${page}</a></li>
        </c:forEach>
        <li><a href="controller?action=paging&page=next">»</a></li>
    </ul>
</div>
<div class="footer">
    <hr width="100%"/>
    Copyright © 智捷课堂 2008-2018. All Rights Reserved
</div>
</body>
</html>
