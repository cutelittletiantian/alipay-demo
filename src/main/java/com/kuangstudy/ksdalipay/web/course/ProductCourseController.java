package com.kuangstudy.ksdalipay.web.course;

import com.kuangstudy.ksdalipay.entity.ProductCourse;
import com.kuangstudy.ksdalipay.service.AlipayService;
import com.kuangstudy.ksdalipay.service.course.ProductCourseService;
import com.kuangstudy.ksdalipay.vo.PayVo;
import com.kuangstudy.ksdalipay.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description:
 * @author: xuke
 * @time: 2021/4/2 0:53
 */
@Controller
public class ProductCourseController {

    @Autowired
    private ProductCourseService productCourseService;

    @PostMapping("/api/load/course")
    @ResponseBody
    public R loadCourse() {
        List<ProductCourse> courseList = productCourseService.list();
        return R.ok().data("courseList", courseList);
    }
}
