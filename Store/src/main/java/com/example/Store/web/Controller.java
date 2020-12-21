package com.example.Store.web;

import com.example.Store.domain.Customer;
import com.example.Store.domain.Goods;
import com.example.Store.service.CustomerService;
import com.example.Store.service.GoodsService;
import com.example.Store.service.ServiceException;
import com.example.Store.service.imp.CustomerServiceImp;
import com.example.Store.service.imp.GoodsServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "Controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private CustomerService customerService = new CustomerServiceImp();
    private GoodsService goodsService = new GoodsServiceImp();

    private int totalPageNumber = 0;    // 总页数
    private int pageSize = 10;          // 每页行数
    private int currentPage = 1;        // 当前页面


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //客户提交的操作
        String action = request.getParameter("action");

        if ("reg".equals(action)) {
            //客户注册
            String userid = request.getParameter("userid");
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            String birthday = request.getParameter("birthday");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");

            //服务器端验证
            List<String> errors = new ArrayList<>();
            if (userid == null || userid.equals("")) {
                errors.add("客户账户不能为空");
            }
            if (name == null || name.equals("")) {
                errors.add("客户姓名不能为空");
            }
            if (password == null
                    || password2 == null
                    || !password.equals(password2)) {
                errors.add("两次输入的密码不一致");
            }
            if (errors.size() > 0) { // 验证失败
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("customer_reg.jsp").forward(request, response);
            } else { // 验证成功
                Customer customer = new Customer();
                customer.setId(userid);
                customer.setName(name);
                customer.setPassword(password);
                customer.setAddress(address);
                customer.setPhone(phone);
                try {
                    Date d = dateFormat.parse(birthday);
                    customer.setBirthday(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //注册
                try {
                    customerService.register(customer);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } catch (ServiceException e) {
                    // 客户id已经注册
                    errors.add("客户ID 已经注册");
                    request.setAttribute("errors", errors);
                    request.getRequestDispatcher("customer_reg.jsp").forward(request, response);
                }
            }
        } else if ("login".equals(action)) {
            //客户登陆
            String userid = request.getParameter("userid");
            String password = request.getParameter("password");

            Customer customer = new Customer();
            customer.setId(userid);
            customer.setPassword(password);

            if (customerService.login(customer)) {
                // 登陆成功
                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);
                request.getRequestDispatcher("main.jsp").forward(request, response);
            } else {
                // 登陆失败
                List<String> errors = new ArrayList<>();
                errors.add("您输入的账户或密码错误");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else if ("list".equals(action)) {
            // 商品列表
            List<Goods> goodsList = goodsService.queryAll();

            if (goodsList.size() % pageSize == 0) {
                totalPageNumber = goodsList.size() / pageSize;
            } else {
                totalPageNumber = goodsList.size() / pageSize + 1;
            }

            request.setAttribute("totalPageNumber", totalPageNumber);
            request.setAttribute("currentPage", currentPage);

            int start = (currentPage - 1) * pageSize;
            int end = currentPage * pageSize;

            if (currentPage == totalPageNumber) {
                end = goodsList.size();
            }

            request.setAttribute("goodsList", goodsList.subList(start, end));
            request.getRequestDispatcher("goods_list.jsp").forward(request, response);

        } else if ("paging".equals(action)) {
            // 商品列表分页
            String page = request.getParameter("page");

            if (page.equals("prev")) {//向上翻页
                currentPage--;
                if (currentPage < 1) {
                    currentPage = 1;
                }
            } else if (page.equals("next")) {//向下翻页
                currentPage++;
                if (currentPage > totalPageNumber) {
                    currentPage = totalPageNumber;
                }
            } else {
                currentPage = Integer.valueOf(page);
            }

            int start = (currentPage - 1) * pageSize;
            int end = currentPage * pageSize;

            List<Goods> goodsList = goodsService.queryByStartEnd(start, end);

            request.setAttribute("totalPageNumber", totalPageNumber);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("goodsList", goodsList);
            request.getRequestDispatcher("goods_list.jsp").forward(request, response);

        } else if ("detail".equals(action)){
            // 商品详情
            String goodsid = request.getParameter("id");
            Goods goods = goodsService.queryDetail(new Long(goodsid));
            request.setAttribute("goods", goods);
            request.getRequestDispatcher("goods_detail.jsp").forward(request,response);
        }

    }
}
