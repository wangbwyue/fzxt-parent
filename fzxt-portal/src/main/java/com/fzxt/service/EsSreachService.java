package com.fzxt.service;

import com.fzxt.model.Course;
import com.fzxt.response.QueryResult;


public interface EsSreachService {

    QueryResult<Course> searchCourseList(Course course);
}
