package com.github.pchudzik.gae.test.servlet.spring;

import com.github.pchudzik.gae.test.config.GoogleAppEngineKeyPropertyEditor;
import com.github.pchudzik.gae.test.dao.StudentDao;
import com.github.pchudzik.gae.test.domain.Student;
import com.github.pchudzik.gae.test.repository.StudentRepository;
import com.google.appengine.api.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * User: pawel
 * Date: 20.07.13
 * Time: 11:27
 */
@Controller
@RequestMapping("/student")
public class StudentController {
	public static final String EDIT_PAGE = "/student/edit";
	@Autowired private StudentDao studentDao;
	@Autowired private StudentRepository studentRepository;

	@RequestMapping(value = "/edit", method = GET)
	public String getEditPage() {
		return EDIT_PAGE;
	}

	@RequestMapping(value = "/edit/{id}", method = GET)
	public String getEditPage(@PathVariable Key id, @RequestParam String type, ModelMap modelMap) {
		Student student = null;
		if("raw".equals(type)) {
			student = studentDao.findOne(id);
		} else if("repo".equals(type)) {
			student = studentRepository.findOne(id);
		} else {
			throw new IllegalArgumentException();
		}

		Assert.notNull(student);
		modelMap.put("student", student);
		return EDIT_PAGE;
	}

	@RequestMapping(value = "/edit", method = POST)
	public String saveStudent(Student student, ModelMap modelMap,
							  @RequestParam String saveMethod) {

		String msg = null;
		//ugly
		if("Save raw".equals(saveMethod)) {
			studentDao.save(student);
			msg = "Student saved in store using raw entity manager";
		} else if("Save repo".equals(saveMethod)) {
			studentRepository.save(student);
			msg = "Student saved in store using spring data";
		}

		modelMap.put("msg", msg);
		modelMap.put("object", student);

		return "/success";
	}

	@RequestMapping(value = "/edit/{id}", method = POST)
	public String saveEditedStudent(Student student, ModelMap modelMap, @RequestParam String saveMethod) {
		return saveStudent(student, modelMap, saveMethod);
	}

	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable Key id, @RequestParam String type, ModelMap modelMap) {
		String msg = "Student with id " + id + " deleted using ";
		if("raw".equals(type)) {
			studentDao.delete(id);
			msg += "raw entity manager";
		} else if("repo".equals(type)){
			studentRepository.delete(id);
			msg += "spring data repository";
		}
		modelMap.put("msg", msg);
		return "/success";
	}

	@RequestMapping(value = "/list")
	public String listStudents(@RequestParam String type, ModelMap modelMap) {
		List<Student> studens = null;
		if("raw".equals(type)) {
			studens = studentDao.findAll();
		} else if("repo".equals(type)) {
			studens = studentRepository.findAll();
		} else {
			throw new IllegalArgumentException();
		}
		modelMap.put("students", studens);
		return "/student/list";
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(Key.class, new GoogleAppEngineKeyPropertyEditor());

	}

	@ModelAttribute("student")
	public Student getStudent() {
		return new Student();
	}
}
