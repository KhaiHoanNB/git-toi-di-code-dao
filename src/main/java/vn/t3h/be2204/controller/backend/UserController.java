package vn.t3h.be2204.controller.backend;

import org.apache.catalina.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.t3h.be2204.dto.UserDto;

import javax.validation.Valid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("backend/user")
public class UserController {

    //1: Tạo bảng USER trong csdl (FULL_NAME, EMAIL, PASSWORD)
    // và thêm 5 bản ghi vào cơ sở dữ liệu
    //2: Thêm thư viên jdbc bằng maven (pom) rồi kết nối
    // truy vấn tất cả bản ghi từ trong csdl
    // 3: Hiển thị danh sách user lên giao diên

    private static String URL = "jdbc:mysql://localhost:3306/t3h";
    private static String USER = "root";
    private static String PASS = "123456789";

    @RequestMapping("list")
    public String list(Model model) throws Exception{
        List<UserDto> userDtoList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USER, PASS);

        String queryString = "select * from user";
        PreparedStatement pst = connection.prepareStatement(queryString);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("ID");
            String name = resultSet.getString("FULL_NAME");
            String email = resultSet.getString("EMAIL");
            String password = resultSet.getString("PASSWORD");
            userDtoList.add(new UserDto(id, name, email, password, null, null));
        }
        connection.close();
        pst.close();

        model.addAttribute("list", userDtoList);
        return "backend/user/user_list";
    }

    @RequestMapping("detail/{id}")
    public String detail(Model model, @PathVariable Long id) throws Exception{
        model.addAttribute("user", detailById(id));
        return "backend/user/user_detail";
    }

    @RequestMapping("rest-detail/{id}")
    @ResponseBody
    public UserDto getDetail( @PathVariable Long id) throws Exception{
        return detailById(id);
    }

    //1: Thêm thư viên vào maven
    //2: Thêm @valid và BindingResult và custom kiểu valid và nội dung message lỗi
    //3: chỉnh sửa dưới jsp

    @RequestMapping("create")
    public String create(Model model) throws Exception{
        UserDto dto = new UserDto();
        model.addAttribute("userDto", dto);
        return "backend/user/user_create";
    }

    @RequestMapping("update/{id}")
    public String update(Model model, @PathVariable Long id) throws Exception{
        UserDto dto = detailById(id);
        model.addAttribute("userDto", dto);
        return "backend/user/user_update";
    }
    @RequestMapping(value = "save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(@Valid @ModelAttribute UserDto userDto,
                       BindingResult bindingResult,// ngay sau biến có @Valid, chứa kết quả sau khi valid
                       RedirectAttributes model) throws Exception{
        if (bindingResult.hasErrors()) {
//            model.addAttribute("userDto", userDto);
            return "backend/user/user_create";
        }
        // lưu user vào csdl
        saveDb(userDto);
        model.addFlashAttribute("message", "Lưu tài khoản thành công");
        return "redirect:/signin";

//        return "/WEB-INF/jsp/signin.jsp";
    }

    @RequestMapping(value = "save-body", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveBody(@RequestBody UserDto userDto) throws Exception{
        // lưu user vào csdl
        saveDb(userDto);
        return "Lưu tài khoản thành công";
    }


    UserDto detailById(Long id) throws Exception {
        UserDto userDto = null;
        Connection connection = DriverManager.getConnection(URL, USER, PASS);

        String queryString = "select * from user where ID = ?" ;
        PreparedStatement pst = connection.prepareStatement(queryString);
        pst.setLong(1, id);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("FULL_NAME");
            String email = resultSet.getString("EMAIL");
            String password = resultSet.getString("PASSWORD");
            userDto = new UserDto(id, name, email, password, null, null);
            if (userDto != null) break;
        }
        connection.close();
        pst.close();
        return userDto;
    }

    void saveDb(UserDto userDto) throws Exception {
        Connection connection = DriverManager.getConnection(URL, USER, PASS);

        String queryString = "insert into user (FULL_NAME, EMAIL, PASSWORD) value (?,?,?)" ;
        PreparedStatement pst = connection.prepareStatement(queryString);
        pst.setString(1, userDto.getFullName());
        pst.setString(2, userDto.getEmail());
        pst.setString(3, userDto.getPassword());
        pst.executeUpdate();
        connection.close();
        pst.close();
    }

}
