package com.github.pchudzik.gae.test.servlet.spring;

import com.github.pchudzik.gae.test.config.GoogleAppEngineKeyPropertyEditor;
import com.github.pchudzik.gae.test.domain.Student;
import com.github.pchudzik.gae.test.service.StudentService;
import com.github.pchudzik.gae.test.service.StudentService.AccessMethod;
import com.google.appengine.api.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
	public static final String SUCCESS_PAGE = "/success";

	@Autowired private StudentService studentService;

	@RequestMapping(value = "/edit", method = GET)
	public String getEditPage() {
		return EDIT_PAGE;
	}

	@RequestMapping(value = "/edit/{id}", method = GET)
	public String getEditPage(@PathVariable Key id, @RequestParam String type, ModelMap modelMap) {
		Student student = studentService.findOne(id, getAccessMethod(type));

		Assert.notNull(student);

		modelMap.put("student", student);
		return EDIT_PAGE;
	}

	private AccessMethod getAccessMethod(String accessString) {
		return "raw".equals(accessString) || "Save raw".equals(accessString)
				? AccessMethod.RAW_EM
				: AccessMethod.SPRING_REPO;
	}

	@RequestMapping(value = "/edit", method = POST)
	public String saveStudent(Student student, ModelMap modelMap,
							  @RequestParam String saveMethod) {

		AccessMethod accessMethod = getAccessMethod(saveMethod);
		studentService.saveStudent(student, accessMethod);

		modelMap.put("msg", accessMethod == AccessMethod.RAW_EM
							? "Student saved in store using raw entity manager"
							: "Student saved in store using spring data");
		modelMap.put("object", student);

		return SUCCESS_PAGE;
	}

	@RequestMapping(value = "/edit/{id}", method = POST)
	public String saveEditedStudent(Student student, ModelMap modelMap, @RequestParam String saveMethod) {
		return saveStudent(student, modelMap, saveMethod);
	}

	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable Key id, @RequestParam("type") String deleteType, ModelMap modelMap) {
		AccessMethod accessMethod = getAccessMethod(deleteType);
		studentService.delete(id, accessMethod);

		modelMap.put("msg", "Student with id " + id + " deleted using " + (accessMethod == AccessMethod.RAW_EM
							? "raw entity manager"
							: "spring data repository"));
		return SUCCESS_PAGE;
	}

	@RequestMapping(value = "/list")
	public String listStudents(@RequestParam("type") String accessType, ModelMap modelMap) {
		modelMap.put("students", studentService.findAll(getAccessMethod(accessType)));
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
