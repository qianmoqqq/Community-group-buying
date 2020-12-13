package com.example.demo1.control;

import com.example.demo1.entity.Type;
import com.example.demo1.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/type", method = RequestMethod.POST)
public class TypeControl {
    @Autowired
    private TypeService typeService;


    @RequestMapping("/add")//管理员添加新的商品种类
    public String addControl(HttpServletRequest request) {
        boolean flag;
        Type type;
        String name = request.getParameter("name");
        String next = request.getParameter("text");
        type = typeService.selectTypeServiceByName(name);
        if (type == null) {
            type = new Type();
            type.setT_name(name);
            type.setT_next(next);
            flag = typeService.saveTypeService(type);
            if (flag)
                return "page";//添加成功，返回页面
            return "errorpage";//未知错误
        }
        return "errorpage";//该种类名称已存在
    }

    @RequestMapping("/update")//管理员更新某商品种类
    public String updateControl(HttpServletRequest request) {
        boolean flag;
        Type type;
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String next = request.getParameter("text");
        int t_id = Integer.parseInt(id);
        type = typeService.selectTypeServiceById(t_id);
        if (type != null) {
            type = typeService.selectTypeServiceByName(name);
            if (type == null) {
                type = new Type();
                type.setT_id(t_id);
                type.setT_name(name);
                type.setT_next(next);
                flag = typeService.saveTypeService(type);
                if (flag)
                    return "page";//添加成功，返回页面
                return "errorpage";//未知错误
            }
            return "errorpage";//该种类名称已存在
        }
        return "errorpage";//未知错误
    }

    @RequestMapping("/delete")//管理员删除商品种类
    public String deleteTypeControl(int t_id) {
        boolean flag = typeService.deleteTypeService(typeService.selectTypeServiceById(t_id));
        if (flag)
            return "page";//删除成功
        else
            return "errorpage";
    }
}