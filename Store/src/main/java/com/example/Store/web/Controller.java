package com.example.Store.web;

import com.example.Store.domain.Customer;
import com.example.Store.domain.Goods;
import com.example.Store.service.CustomerService;
import com.example.Store.service.GoodsService;
import com.example.Store.service.OrdersService;
import com.example.Store.service.ServiceException;
import com.example.Store.service.imp.CustomerServiceImp;
import com.example.Store.service.imp.GoodsServiceImp;
import com.example.Store.service.imp.OrdersServiceImp;
import com.sun.tools.corba.se.idl.constExpr.Or;

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
import java.util.*;

@WebServlet(name = "Controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private CustomerService customerService = new CustomerServiceImp();
    private GoodsService goodsService = new GoodsServiceImp();
    private OrdersService ordersService = new OrdersServiceImp();

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

        } else if ("detail".equals(action)) {
            // 商品详情
            String goodsid = request.getParameter("id");
            Goods goods = goodsService.queryDetail(new Long(goodsid));
            request.setAttribute("goods", goods);
            request.getRequestDispatcher("goods_detail.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            // 添加购物车
            Integer goodsid = new Integer(request.getParameter("id"));
            String goodsname = request.getParameter("name");
            Float price = new Float(request.getParameter("price"));

            // 购物车结构是List中包含Map，每一个Map对象是一个商品
            // 从Session中取出购物车
            List<Map<String, Object>> cart = (List<Map<String, Object>>) request.getSession().getAttribute("cart");

            if (cart == null) { //第一次取出是null
                cart = new ArrayList<Map<String, Object>>();
                request.getSession().setAttribute("cart", cart);
            }

            // 购物车中有选择的商品
            int flag = 0;

            for (Map<String, Object> item : cart) {
                Integer goodsid2 = (Integer) item.get("goodsid");
                if (goodsid.equals(goodsid2)) {
                    Integer quantity = (Integer) item.get("quantity");
                    quantity++;
                    item.put("quantity", quantity);

                    flag++;
                }
            }

            // 购物车中没有选择的商品
            if (flag == 0) {

                Map<String, Object> item = new HashMap<>();
                // item结构是Map[商品ID, 商品名称, 价格, 数量]

                item.put("goodsid", goodsid);
                item.put("goodsname", goodsname);
                item.put("quantity", 1);
                item.put("price", price);

                cart.add(item);
            }

            System.out.println(cart);

            String pageName = request.getParameter("pagename");

            if (pageName.equals("list")) {
                int start = (currentPage - 1) * pageSize;
                int end = currentPage * pageSize;

                List<Goods> goodsList = goodsService.queryByStartEnd(start, end);

                request.setAttribute("totalPageNumber", totalPageNumber);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("goodsList", goodsList);
                request.getRequestDispatcher("goods_list.jsp").forward(request, response);

            } else {
                Goods goods = goodsService.queryDetail(new Long(goodsid));
                request.setAttribute("goods", goods);
                request.getRequestDispatcher("goods_detail.jsp").forward(request, response);
            }

        } else if ("cart".equals(action)) {
            //查看购物车
            List<Map<String, Object>> cart = (List<Map<String, Object>>) request.getSession().getAttribute("cart");
            double total = 0.0;

            if (cart != null) {
                for (Map<String, Object> item : cart) {
                    Integer goodsid2 = (Integer) item.get("goodsid");
                    Integer quantity = (Integer) item.get("quantity");
                    Float price = (Float) item.get("price");
                    double subtotal = price * quantity;
                    total += subtotal;
                }
            }

            request.setAttribute("total", total);
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } else if ("sub_ord".equals(action)) {
            //查看购物车
            List<Map<String, Object>> cart = (List<Map<String, Object>>) request.getSession().getAttribute("cart");
            for (Map<String, Object> item : cart) {
                Integer goodsid = (Integer) item.get("goodsid");
                String strquantity = request.getParameter("quantity" + goodsid);
                int quantity = 0;
                try {
                    quantity = new Integer(strquantity);
                } catch (Exception e) {
                    quantity = 0;
                }

                item.put("quantity", quantity);
            }

            //提交订单
            String ordersid = ordersService.submitOrders(cart);
            request.setAttribute("ordersid", ordersid);
            request.getRequestDispatcher("order_finish.jsp").forward(request, response);
            //清楚session
            request.getSession().removeAttribute("cart");
        } else if ("main".equals(action)) {
            int start = (currentPage - 1) * pageSize;
            int end = currentPage * pageSize;

            List<Goods> goodsList = goodsService.queryByStartEnd(start, end);

            request.setAttribute("totalPageNumber", totalPageNumber);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("goodsList", goodsList);
            request.getRequestDispatcher("goods_list.jsp").forward(request, response);
        }
    }
}
