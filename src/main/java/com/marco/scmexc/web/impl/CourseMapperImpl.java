package com.marco.scmexc.web.impl;

import com.marco.scmexc.models.domain.Course;
import com.marco.scmexc.models.requests.CourseRequest;
import com.marco.scmexc.models.response.CourseResponse;
import com.marco.scmexc.services.CourseService;
import com.marco.scmexc.web.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CourseMapperImpl implements CourseMapper {

    private final CourseService courseService;

    public CourseMapperImpl(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public List<CourseResponse> getAllCoursesByName(String name) {
        return courseService.getAllCourses(name).stream().map(this::mapCourseToCourseResponse).collect(toList());
    }

    private CourseResponse mapCourseToCourseResponse(Course course) {
        return CourseResponse.of(course.getId(), course.getName(), course.getCode(), course.getDescription(), course.getDateCreated());
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        return mapCourseToCourseResponse(courseService.getCourseById(id));
    }

    @Override
    public CourseResponse addNewCourse(CourseRequest request) {
        Course course = courseService.addNewCourse(request);
        return mapCourseToCourseResponse(course);
    }
}
