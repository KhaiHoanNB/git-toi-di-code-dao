<%@page pageEncoding="UTF-8" %>

<jsp:include page="/WEB-INF/jsp/backend/common/head.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/backend/common/nav.jsp"></jsp:include>
<style>
    .error {
        color: red;
    }
</style>
<div class="container">
    <div>
        <div class="bg-light p-5 rounded">
            <div class="col-sm-8 mx-auto">
                <h1>Cập nhật tài khoản</h1>
                <form id="user-update-form" class="row g-3 needs-validation" method="post" action="/backend/user/update" >
                    <input name="id" value="${userDto.id}" hidden/>
                    <div class="col-md-6">
                        <label for="validationCustom01" class="form-label">Họ và tên</label>
                        <input type="text" class="form-control" id="validationCustom01" name="fullName" value="${userDto.fullName}" />
                    </div>
                    <div class="col-md-6">
                        <label for="validationCustom02" class="form-label">Email</label>
                        <input type="text" class="form-control" id="validationCustom02" name="email" value="${userDto.email}" />
                    </div>
                    <div class="col-12">
                        <button class="btn btn-primary" type="submit">Cập nhật</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $( document ).ready(function() {
        $("#user-update-form").validate({
            rules: {
                // simple rule, converted to {required:true}
                fullName: {
                    required: true
                },
                // compound rule
                email: {
                    required: true,
                    email: true
                }
            },
            messages: {
                fullName: "Họ và tên bắt buộc phải nhập",
                email: {
                    required: "Email bắt buộc phải nhập",
                    email: "Không đúng định dạng email"
                }
            }
        });
    });

</script>

<jsp:include page="/WEB-INF/jsp/backend/common/footer.jsp"></jsp:include>


















