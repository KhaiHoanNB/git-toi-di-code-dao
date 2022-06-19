<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
<div class="container">
    <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Danh sách tài khoản</p>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">STT</th>
            <th scope="col">Họ và tên</th>
            <th scope="col">Email</th>
            <th scope="col">Mật khẩu</th>
            <th scope="col">Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="user">
            <tr>
                <th scope="col">${user.id}</th>
                <th scope="col">${user.fullName}</th>
                <th scope="col">${user.email}</th>
                <th scope="col">${user.password}</th>
                <th scope="col"><a href="#">Xóa</a> &nbsp;<a href="#">Sửa</a></th>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"></jsp:include>